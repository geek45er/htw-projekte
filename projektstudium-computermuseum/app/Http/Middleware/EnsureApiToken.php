<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;

class EnsureApiToken
{
    /**
     * Lässt die anfrage nur durch, wenn der korrekte api-token als
     * "Authorization: Bearer <token>"-header mitgeschickt wurde.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle(Request $request, Closure $next)
    {
        $expected = config('api.token');

        // fail closed: ohne konfigurierten token ist die api komplett zu
        if (empty($expected) || !hash_equals($expected, (string) $request->bearerToken())) {
            return response()->json(['error' => 'Unauthorized'], 401);
        }

        return $next($request);
    }
}
