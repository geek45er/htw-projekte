# projektstudium computermuseum

mein beitrag zur website des htw-computermuseums (laravel, projektstudium sose 2026). die übrige website gehört der projektgruppe und ist hier nicht enthalten.

- `routes/api.php`, `app/Http/Controllers/Api/ExhibitController.php` lesende rest-api für exponate (`/ping`, `/exhibits`, `/exhibits/{id}`, `/exhibits/search`)
- `app/Http/Middleware/EnsureApiToken.php` zugriff nur mit bearer-token aus der `.env`
- `app/Http/Middleware/ForceJsonResponse.php` antworten immer als json
- `config/api.php` konfiguration für den token
- `composer.json` alle paketversionen fest gepinnt
