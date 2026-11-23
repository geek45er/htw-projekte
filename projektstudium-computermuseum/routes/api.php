<?php

use App\Http\Controllers\Api\ExhibitController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

// einfacher test-endpunkt, prüft ob die api überhaupt erreichbar ist
Route::get('/ping', function () {
    return ['status' => 'ok'];
});

// alle exhibit-routen erfordern den api-token
// (Authorization: Bearer <API_TOKEN aus .env>)
Route::middleware('api.token')->group(function () {

    // alle sichtbaren exponate abrufen
    Route::get('/exhibits', [ExhibitController::class, 'index']);

    // exponate nach kriterien filtern (muss VOR {id}-route stehen)
    Route::get('/exhibits/search', [ExhibitController::class, 'search']);

    // ein einzelnes exponat anhand der id abrufen
    // .+ erlaubt schrägstriche in der id (z.B. "T/2007/06/00081")
    Route::get('/exhibits/{id}', [ExhibitController::class, 'show'])->where('id', '.+');

});

// schreibende endpunkte (anlegen/bearbeiten/löschen) sind bewusst nicht
// implementiert, die api ist erstmal nur lesend und intern gedacht.
// kann man bei bedarf später mit auth:sanctum absichern und ergänzen.
