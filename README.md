# Git Workflow — Do This Before You Code

Before you touch any Java, you need to set up your Git repository. Every challenge lives in its own branch, and every meaningful step you take gets its own commit. This is non-negotiable — it's how real development works, and it's how you'll build the habit from day one.

---

## 1. Create a remote repository

1. Go to your gitlab account home page.
2. Click **New Project** and select **Create blank project**.
3. Name it something like `java-base64-challenges`. Leave it **Public** and do **not** initialize it with a README by unchecking the **Initialize repository with a README** checkbox— you'll do that yourself.
4. Click **Create project** and leave the page open — you'll need the URL in a moment.

---

## 2. Initialize your local repository

Open a terminal, navigate to the folder where you'll keep your code, and run:

```bash
git init java-base64-challenges
cd java-base64-challenges
```

This creates a new folder and turns it into a Git repository. Now connect it to GitHub:

```bash
git remote add origin https://github.com/YOUR_USERNAME/java-base64-challenges.git
```

Create a first file, make your first commit, and push:

```bash
echo "# Java Base64 Challenges" > README.md
git add README.md
git commit -m "chore: initialize repository"
git push -u origin main
```

> **What just happened?** `git add` stages changes (tells Git "I want to include this"), `git commit` saves a snapshot with a message, and `git push` sends it to GitHub. The `-u origin main` flag sets `origin/main` as the default push target so next time you only need `git push`.

---

## 3. Work on a branch per challenge

**Never code directly on `main`.** For each challenge, create a dedicated branch:

```bash
# Starting Challenge 1
git checkout -b challenge/01-encode-string
```

The naming convention `challenge/01-encode-string` keeps branches organized and easy to find.

When a challenge is fully done and working, merge it back into `main`:

```bash
git checkout main
git merge challenge/01-encode-string
git push
```

---

## 4. Commit at every major step — not just at the end

A commit is a checkpoint. If something breaks, you can always go back. The rule of thumb: **if you can describe what you just did in one sentence, commit it.**

Here is a concrete example of what that looks like for Challenge 3:

```bash
# Step 1 — set up the project structure
git add .
git commit -m "feat: scaffold Challenge 3 file encoder class"

# Step 2 — implement reading the input file
git add .
git commit -m "feat: read input file path from command-line argument"

# Step 3 — implement the encoding logic
git add .
git commit -m "feat: encode file bytes to Base64 string"

# Step 4 — implement writing the output file
git add .
git commit -m "feat: write Base64 output to .b64.txt file"

# Step 5 — add error handling for missing files
git add .
git commit -m "fix: handle missing input file with clear error message"
```

> **Tip on commit messages:** Start with a short verb like `feat:`, `fix:`, `refactor:`, or `test:`. This style is called [Conventional Commits](https://www.conventionalcommits.org/) and is widely used in the industry.

---

## 5. Open a Pull Request (PR) when a challenge is done

A Pull Request is how you propose that your branch's work gets merged into `main`. Even if you're working alone, the practice is valuable.

1. Push your branch to GitHub:
   ```bash
   git push origin challenge/01-encode-string
   ```
2. Go to your repository on GitHub. You'll see a yellow banner — click **Compare & pull request**.
3. Give your PR a title like `Challenge 1 — Encode a string` and write a short description of what you implemented.
4. Click **Create pull request**.
5. Review the diff (the list of changes). When you're happy, click **Merge pull request** → **Confirm merge**.

---

## Quick reference — commands you'll use every day

| Action | Command |
|---|---|
| Check what has changed | `git status` |
| See your commit history | `git log --oneline` |
| Create and switch to a new branch | `git checkout -b branch-name` |
| Stage all changes | `git add .` |
| Commit staged changes | `git commit -m "your message"` |
| Push current branch | `git push` |
| Switch to an existing branch | `git checkout branch-name` |
| Merge a branch into the current one | `git merge branch-name` |

---

## Suggested branch & commit checklist per challenge

Use this as a template. Adapt the steps to whatever the challenge actually requires.

- [ ] `git checkout -b challenge/XX-short-name`
- [ ] Scaffold the Java class → commit
- [ ] Implement core logic → commit
- [ ] Test with the examples given in the spec → commit
- [ ] Add input validation / error handling → commit
- [ ] Final clean-up (remove debug prints, tidy imports) → commit
- [ ] `git push origin challenge/XX-short-name`
- [ ] Open a Pull Request on GitHub → merge into `main`

---

# Java networking challenges

A progressive set of 8 challenges covering raw TCP sockets, concurrency, UDP, and HTTP. All examples use the Java standard library only — no frameworks or external dependencies needed.

---

## Warm-up

### Challenge 1 — TCP echo server

Build a server that listens on a port, accepts one client at a time, reads lines of text, and echoes each line back with a prefix. Test it using `telnet localhost 9000`.

**Hint:** Use `ServerSocket` to bind to a port and `server.accept()` to block until a client connects. Wrap the socket's streams in `BufferedReader` and `PrintWriter` for line-by-line I/O. Set `autoFlush: true` on the `PrintWriter` so replies are sent immediately.

**Done when:**
- Server starts without error and prints a "listening" message
- A `telnet` client can connect and receive echoes in real time
- Each reply is prefixed, e.g. `ECHO: hello`
- Server keeps running after the client disconnects
- Socket and streams are closed in a `finally` block or try-with-resources

---

### Challenge 2 — TCP client

Write the matching client for challenge 1. Connect to the server, send 5 messages from an array, read each echo back, and print the round-trip result. Close the connection cleanly when done.

**Hint:** Use `new Socket("localhost", 9000)` to connect. Use try-with-resources to ensure the socket always closes, even on exceptions. The `PrintWriter` / `BufferedReader` pattern from challenge 1 applies here too.

**Done when:**
- Client connects, sends all 5 messages, and receives 5 echoes
- Each sent message and its echo are printed side by side
- Connection closes cleanly — server should log the disconnect
- Everything is wrapped in try-with-resources so the socket always closes
- Program exits with code 0 (no hanging threads)

---

## HTTP

### Challenge 3 — HTTP GET with HttpClient

Use Java 11's `HttpClient` to fetch a public JSON API (e.g. `https://httpbin.org/get`), print the status code, selected response headers, and pretty-print the body.

**Hint:** Build a client with `HttpClient.newHttpClient()` and a request with `HttpRequest.newBuilder().uri(...).header(...).GET().build()`. Use `HttpResponse.BodyHandlers.ofString()`. For async, chain `.thenAccept()` on the `CompletableFuture` and call `.join()` to wait.

**Done when:**
- Status code, `Content-Type`, and `Date` headers are printed
- Response body is printed (formatted JSON is a bonus)
- A non-200 status is handled explicitly with a meaningful message
- Request includes a custom `User-Agent` header
- Both synchronous (`send`) and async (`sendAsync`) versions are implemented

---

### Challenge 4 — Minimal HTTP server from scratch

Using only `ServerSocket`, build a bare-bones HTTP/1.1 server that serves static files. Return `200` with the file contents, `404` for unknown paths, and `405` for non-GET methods.

**Hint:** Read the first line with `BufferedReader.readLine()` — it looks like `GET /index.html HTTP/1.1`. Split on spaces and take index 1 for the path. A minimal valid response is `HTTP/1.1 200 OK\r\nContent-Length: N\r\n\r\n` followed by the body bytes. You must drain the remaining request headers before writing the response.

**Done when:**
- A real browser can load `http://localhost:8080/index.html` and render it correctly
- Response includes correct `Content-Length` and `Content-Type` headers
- Requesting a missing file returns a proper `404` HTML page
- A POST request to any path returns `405 Method Not Allowed`
- The blank line separating headers from body (`\r\n\r\n`) is always present

---

- [Java SE 21 — `java.net` package Javadoc](https://docs.oracle.com/en/java/docs/books/tutorial/networking/sockets/index.html)
- [Java 11 `HttpClient` guide](https://openjdk.org/groups/net/httpclient/intro.html)
- [RFC 793 — Transmission Control Protocol](https://datatracker.ietf.org/doc/html/rfc793)
- [RFC 7230 — HTTP/1.1 Message Syntax](https://datatracker.ietf.org/doc/html/rfc7230)
