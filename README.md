# PatientSys - Google Flash API integration

This project replaces the local Olama model call with an optional Google Flash (Generative) API integration.

Environment variables
- `GOOGLE_API_KEY` (recommended): set to your Google API key (kept out of source control).
- `GOOGLE_FLASH_ENDPOINT` (optional): custom endpoint; defaults to Google Generative Language flash model endpoint.

Behavior
- If `GOOGLE_API_KEY` is set, the app will call the configured Google endpoint.
- If unset, the app falls back to the local Olama endpoint at `http://localhost:11434/api/generate`.

How to obtain credentials

Option A — API Key (simple)
1. In Google Cloud Console enable the "Generative Language API" for your project.
2. Go to "APIs & Services" → "Credentials" → "Create Credentials" → "API key".
3. Copy the API key and set it in the environment:
```powershell
setx GOOGLE_API_KEY "your_google_api_key_here"
```

Option B — OAuth access token (recommended for production)
1. Use a service account with appropriate IAM permissions and either obtain an access token via `gcloud` or generate one server-side.
2. With the Google Cloud SDK, you can run:
```powershell
gcloud auth application-default print-access-token
```
3. Set the printed token (it starts with `ya29.`) as the env var:
```powershell
setx GOOGLE_API_KEY "ya29..."
```

Notes
- The app accepts either an API key (sent as `?key=...` in the request URL) or an OAuth access token (sent as `Authorization: Bearer ...`).
- For production, prefer service accounts and scoped tokens. Keep secrets out of source control and rotate keys/tokens regularly.

Attachments
- You can attach an image or PDF from the UI. The client will base64-encode the file and send it to the Google API for analysis. The prompt instructs the model to decode and analyze the file for medical findings.

Privacy & Security
- Attachments and prompts (which may contain PHI) are sent to the configured model endpoint. Ensure you have proper consent and secure transmission (use Google Cloud with proper IAM and audit controls for production).

Running locally
1. Compile:
```bash
javac -d out src/PatientSystemGUI.java
```
2. Run (Windows PowerShell / cmd):
```powershell
setx GOOGLE_API_KEY "YOUR_API_KEY_HERE"
java -cp out PatientSystemGUI
```

Notes
- Keep your API key secret. Do not commit it to the repository.
- The code performs heuristic extraction of the model response; you may need to adapt the extraction for your specific Google model response format.
