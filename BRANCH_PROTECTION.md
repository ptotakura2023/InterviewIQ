# Branch Protection Rules for InterviewIQ

After pushing your code to GitHub, you should protect the `main` and `develop` branches to prevent accidental direct commits.

---

## 🛡️ Why Protect Branches?

| Without Protection | With Protection |
|-------------------|-----------------|
| ❌ Anyone can push directly to main | ✅ Must create Pull Request (PR) |
| ❌ No code review | ✅ Code must be reviewed before merge |
| ❌ Can accidentally break production | ✅ Can require tests to pass first |
| ❌ No audit trail | ✅ All changes documented in PRs |

---

## 📋 How to Set Up Branch Protection on GitHub

### Step 1: Go to Repository Settings

1. Go to your GitHub repository: `https://github.com/YOUR_USERNAME/InterviewIQ`
2. Click **Settings** (top tab)
3. In the left sidebar, click **Branches**

### Step 2: Protect `main` Branch

1. Click **"Add rule"** or **"Add branch protection rule"**
2. In **Branch name pattern**, type: `main`
3. Check these boxes:

   #### Required Settings:
   - ✅ **Require a pull request before merging**
     - ✅ Require approvals: `1` (you can approve your own PRs for now)
     - ✅ Dismiss stale pull request approvals when new commits are pushed
   
   - ✅ **Require status checks to pass before merging** (optional for now)
   
   - ✅ **Require conversation resolution before merging**
   
   - ✅ **Require linear history** (prevents messy merge commits)
   
   - ✅ **Include administrators** (enforces rules even for repo owner — YOU!)

   #### Optional (Add Later):
   - ⬜ Require deployments to succeed before merging
   - ⬜ Lock branch (rarely used)

4. Click **"Create"** or **"Save changes"**

### Step 3: Protect `develop` Branch

1. Click **"Add rule"** again
2. In **Branch name pattern**, type: `develop`
3. Check the **same boxes as for main**
4. Click **"Create"**

---

## 🔄 How Protection Changes Your Workflow

### Before Protection (❌ Don't do this):
```bash
git checkout main
git add .
git commit -m "Quick fix"
git push origin main    # ❌ This will work but is BAD practice
```

### After Protection (✅ Correct way):
```bash
# 1. Create feature branch
git checkout develop
git checkout -b feature/quick-fix

# 2. Make changes and commit
git add .
git commit -m "Quick fix"
git push origin feature/quick-fix

# 3. Go to GitHub and create Pull Request
#    develop ← feature/quick-fix

# 4. Review and merge via GitHub UI
```

---

## 🎯 Pull Request (PR) Workflow

### Creating a Pull Request on GitHub:

1. **Push your feature branch** to GitHub:
   ```bash
   git push origin feature/IIQ-AUTH-01
   ```

2. **Go to GitHub repository** - You'll see a yellow banner:
   ```
   feature/IIQ-AUTH-01 had recent pushes
   [Compare & pull request]
   ```

3. **Click "Compare & pull request"**

4. **Fill in PR details:**
   - **Base:** `develop` ← **Compare:** `feature/IIQ-AUTH-01`
   - **Title:** `[IIQ-AUTH-01] Add User Registration Feature`
   - **Description:**
     ```markdown
     ## What does this PR do?
     - Adds User entity and repository
     - Creates registration endpoint
     - Implements password encryption
     - Adds registration form UI
     
     ## Related User Story
     IIQ-AUTH-01
     
     ## Testing
     - [ ] Unit tests pass
     - [ ] Manual testing completed
     ```

5. **Click "Create pull request"**

6. **Review your own code** (good practice even for solo projects!)

7. **Click "Merge pull request"** → **Confirm merge**

8. **Delete the branch** (GitHub will offer this option)

---

## 🔄 After Merging a PR

```bash
# 1. Switch to develop
git checkout develop

# 2. Pull the merged changes
git pull origin develop

# 3. Delete local feature branch
git branch -d feature/IIQ-AUTH-01

# 4. Start next feature
git checkout -b feature/IIQ-AUTH-02
```

---

## 🚨 Emergency: Need to Disable Protection Temporarily

**Scenario:** You need to make a quick fix directly to `main` (emergency only!)

### Disable Protection:
1. Go to Settings → Branches
2. Click **Edit** next to the `main` rule
3. Scroll down and click **"Delete rule"**
4. Make your emergency commit
5. **Immediately re-enable protection** using the steps above

**⚠️ Warning:** This should be extremely rare. Almost always use the proper PR workflow!

---

## 📊 Protection Rules Summary

| Branch | Protected? | Requires PR? | Requires Review? |
|--------|-----------|-------------|------------------|
| `main` | ✅ Yes | ✅ Yes | ✅ Yes (1 approval) |
| `develop` | ✅ Yes | ✅ Yes | ✅ Yes (1 approval) |
| `feature/*` | ❌ No | ❌ No | ❌ No |

---

## 💡 For Solo Developers (You!)

**"But I'm the only developer. Why review my own code?"**

Great question! Here's why it's still valuable:

1. **Forces you to read your code again** - Catches obvious mistakes
2. **Documents what changed** - Easy to see history of changes
3. **Good habit for future** - When you work in teams, you'll already know the workflow
4. **GitHub Activity** - Your profile shows contribution activity (good for portfolio!)
5. **Rollback is easier** - Can revert entire PRs instead of individual commits

---

## 🎓 Learning Resources

- [GitHub Docs: Branch Protection Rules](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches/about-protected-branches)
- [Atlassian: Pull Requests](https://www.atlassian.com/git/tutorials/making-a-pull-request)

---

**Next Steps:**
1. ✅ Set up branch protection for `main` and `develop`
2. ✅ Test the workflow by creating your first feature branch
3. ✅ Create your first Pull Request
4. 🚀 Start building InterviewIQ!
