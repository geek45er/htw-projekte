<?php

return [

    /*
    |--------------------------------------------------------------------------
    | API Token
    |--------------------------------------------------------------------------
    |
    | Statischer Token für die interne, rein lesende REST-API. Clients müssen
    | ihn als "Authorization: Bearer <token>"-Header mitschicken. Ist kein
    | Token konfiguriert, lehnt die Middleware alle Anfragen ab (fail closed).
    |
    */

    'token' => env('API_TOKEN'),

];
