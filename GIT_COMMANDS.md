# Git Commands Reference - InterviewIQ

This file contains all Git commands you'll need, explained step by step.

---

## 🚀 Initial Setup (One-Time Only)

### Step 1: Navigate to your project folder
```bash
# Create a folder for your project
mkdir InterviewIQ
cd InterviewIQ

# Copy all the files from this repository structure into this folder
```

### Step 2: Initialize Git
```bash
git init
```
**What this does:** Creates a hidden `.git` folder that tracks all your changes.

### Step 3: Add all files to staging
```bash
git add .
```
**What this does:** Prepares all files to be committed (the `.` means "all files").

### Step 4: Create your first commit
```bash
git commit -m "Initial commit: Project structure and documentation"
```
**What this does:** Saves a snapshot of your files with a message describing what you did.

### Step 5: Rename branch to main
```bash
git branch -M main
```
**What this does:** Renames the default branch from `master` to `main` (GitHub's standard).

### Step 6: Link to GitHub
```bash
# Replace YOUR_GITHUB_USERNAME with your actual username!
git remote add origin https://github.com/YOUR_GITHUB_USERNAME/InterviewIQ.git
```
**What this does:** Connects your local repository to the GitHub repository you created.

**Example:** If your GitHub username is `arjunkumar`, use:
```bash
git remote add origin https://github.com/arjunkumar/InterviewIQ.git
```

### Step 7: Push to GitHub
```bash
git push -u origin main
```
**What this does:** Uploads your code to GitHub. The `-u` flag sets `origin main` as the default for future pushes.

**You may be asked for authentication:**
- Use a **Personal Access Token** (not your password)
- Or set up SSH keys (more secure)

### Step 8: Create develop branch
```bash
git checkout -b develop
git push -u origin develop
```
**What this does:** Creates a new branch called `develop` and uploads it to GitHub.

### Step 9: Return to main
```bash
git checkout main
```

---

## 🌿 Daily Workflow - Working on Features

### Starting a New Feature

```bash
# 1. Make sure you're on develop and have latest code
git checkout develop
git pull origin develop

# 2. Create a new feature branch
git checkout -b feature/IIQ-AUTH-01-user-registration

# 3. Work on your code (edit files, write code)
# ... make changes ...

# 4. Check what files changed
git status

# 5. Add files to staging
git add .
# Or add specific files:
# git add src/main/java/com/interviewiq/controller/AuthController.java

# 6. Commit your changes
git commit -m "Add user registration endpoint"

# 7. Push to GitHub
git push origin feature/IIQ-AUTH-01-user-registration
```

### Making Multiple Commits on Same Feature

```bash
# After first commit and push, continue working...

git add .
git commit -m "Add registration form validation"
git push origin feature/IIQ-AUTH-01-user-registration

# Keep committing as you make progress
git add .
git commit -m "Add unit tests for registration"
git push origin feature/IIQ-AUTH-01-user-registration
```

### Merging Feature to Develop (After PR Approved)

```bash
# 1. Switch to develop
git checkout develop

# 2. Pull latest changes
git pull origin develop

# 3. Merge your feature (usually done via GitHub PR, but manual command is:)
git merge feature/IIQ-AUTH-01-user-registration

# 4. Push to GitHub
git push origin develop

# 5. Delete the feature branch (cleanup)
git branch -d feature/IIQ-AUTH-01-user-registration
git push origin --delete feature/IIQ-AUTH-01-user-registration
```

---

## 🔄 Common Daily Commands

### See what changed
```bash
git status
```

### See commit history
```bash
git log --oneline
```

### See all branches
```bash
git branch -a
```

### Switch branches
```bash
git checkout develop
git checkout main
git checkout feature/IIQ-AUTH-01
```

### Pull latest code from GitHub
```bash
git pull origin develop
```

### Undo changes (before commit)
```bash
# Undo all changes
git reset --hard

# Undo changes to specific file
git checkout -- filename.java
```

### See difference in files
```bash
git diff
```

---

## 🆘 Emergency Commands

### Forgot to create feature branch (already made changes on develop)
```bash
# Stash your changes
git stash

# Create feature branch
git checkout -b feature/IIQ-AUTH-01

# Apply your changes
git stash pop
```

### Need to switch branches but have uncommitted changes
```bash
# Save work temporarily
git stash

# Switch branch
git checkout develop

# Return and restore work
git checkout feature/IIQ-AUTH-01
git stash pop
```

### Made commit on wrong branch
```bash
# If you committed on 'develop' but meant to commit on a feature branch:

# 1. Create feature branch (keeps the commit)
git checkout -b feature/IIQ-AUTH-01

# 2. Go back to develop
git checkout develop

# 3. Reset develop to before your commit
git reset --hard origin/develop
```

---

## 🔐 GitHub Authentication

### Option 1: Personal Access Token (Recommended for beginners)

1. Go to GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Generate new token
3. Give it a name: "InterviewIQ Development"
4. Select scopes: `repo` (full control of private repositories)
5. Generate token and COPY IT (you won't see it again!)
6. When Git asks for password, paste the token

### Option 2: SSH Keys (More secure, no password needed)

*Let me know if you want setup instructions for this*

---

## 📊 Branching Strategy Cheat Sheet

```
main (protected)
  ↑
  └── Only merge from develop or hotfix/*
  
develop (protected)
  ↑
  └── Merge feature/* branches here
  
feature/IIQ-AUTH-01 (your work)
  └── Create from develop, merge back to develop
  
hotfix/fix-login-bug (emergency)
  └── Create from main, merge to main AND develop
```

---

## 💡 Pro Tips

1. **Commit often** - Small, frequent commits are better than large ones
2. **Write clear commit messages** - Future you will thank you
3. **Pull before push** - Always `git pull` before `git push` to avoid conflicts
4. **Never commit directly to main or develop** - Always use feature branches
5. **Use descriptive branch names** - `feature/IIQ-AUTH-01-user-registration` not `feature/stuff`

---

## 📖 Commit Message Format

Good commit messages:
```
✅ "Add user registration endpoint"
✅ "Fix login validation bug"
✅ "Update README with setup instructions"
✅ "Implement JWT token generation"
```

Bad commit messages:
```
❌ "Update"
❌ "Fix bug"
❌ "Changes"
❌ "asdfasdf"
```

---

**Need help?** Ask Vamsi or refer to this guide!
