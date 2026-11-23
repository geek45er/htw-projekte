<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Exhibit;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class ExhibitController extends Controller
{
    // liefert alle sichtbaren exponate für get /api/exhibits
    public function index(): JsonResponse
    {
        $exhibits = Exhibit::where('visible', true) // nur öffentlich sichtbare exponate
            ->orderBy('name')                        // alphabetisch nach name sortieren
            ->get(['id', 'name', 'description']);    // nur diese felder zurückgeben, nicht alles

        return response()->json($exhibits);
    }

    // gibt ein einzelnes exponat per id zurück, für get /api/exhibits/{id}
    public function show(string $id): JsonResponse
    {
        $exhibit = Exhibit::where('visible', true)
            ->where('id', $id)
            ->first(['id', 'name', 'description']); // first() gibt ein objekt zurück (oder null)

        if ($exhibit === null) {
            // 404 heißt "nicht gefunden", ganz normaler http standard
            return response()->json(['error' => 'Exponat nicht gefunden'], 404);
        }

        return response()->json($exhibit);
    }

    // filtert exponate anhand von query-parametern, für get /api/exhibits/search
    public function search(Request $request): JsonResponse
    {
        // erst mal checken ob nur erlaubte parameter genutzt wurden, sonst fehler
        $allowed = ['name', 'description', 'location', 'parent', 'sort', 'direction', 'page', 'per_page'];
        $unknown = array_diff(array_keys($request->query()), $allowed);
        if (!empty($unknown)) {
            return response()->json([
                'error'   => 'Unbekannte Filter-Parameter: ' . implode(', ', $unknown),
                'allowed' => $allowed,
            ], 422);
        }

        // eingaben validieren
        $request->validate([
            'name'        => 'sometimes|string|max:255',
            'description' => 'sometimes|string|max:255',
            'location'    => ['sometimes', 'string', 'regex:/^[A-Z0-9]{2}[A-Z][0-9]$/i'], // z.B. 01A3
            'parent'      => 'sometimes|string|max:255',
            'sort'        => 'sometimes|in:name,created_at',
            'direction'   => 'sometimes|in:asc,desc',
            'page'        => 'sometimes|integer|min:1',
            'per_page'    => 'sometimes|integer|min:1|max:100',
        ]);

        $query = Exhibit::where('visible', true);

        // teiltextsuche auf name, also LIKE '%suchbegriff%'
        // % und _ sind LIKE-wildcards und werden escaped, damit sie als
        // normale zeichen gesucht werden (sonst matcht z.B. "%" alles)
        if ($request->filled('name')) {
            $query->where('name', 'like', '%' . $this->escapeLike($request->input('name')) . '%');
        }

        // teiltextsuche auf description
        if ($request->filled('description')) {
            $query->where('description', 'like', '%' . $this->escapeLike($request->input('description')) . '%');
        }

        // standort: "01A3" → shelf_number=01, shelf_row=A, shelf_compartment=3
        if ($request->filled('location')) {
            $loc = $request->location;
            $query->where('shelf_number',      substr($loc, 0, 2))
                  ->where('shelf_row',         substr($loc, 2, 1))
                  ->where('shelf_compartment', substr($loc, 3, 1));
        }

        // übergeordnetes exponat (exakte id)
        if ($request->filled('parent')) {
            $query->where('parent', $request->parent);
        }

        // standardmäßig nach name sortiert, aufsteigend
        $sort      = $request->input('sort', 'name');
        $direction = $request->input('direction', 'asc');
        $query->orderBy($sort, $direction);

        // standardmäßig 15 einträge pro seite
        $perPage = $request->input('per_page', 15);
        $results = $query->paginate($perPage, ['id', 'name', 'description']);

        return response()->json($results);
    }

    // escaped die LIKE-sonderzeichen % und _ (und den escape-backslash selbst)
    private function escapeLike(string $value): string
    {
        return addcslashes($value, '%_\\');
    }
}
