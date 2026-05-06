#!/bin/bash

# ===================================
# InterviewIQ - Git Setup Script
# ===================================
# This script initializes the repository and sets up the branching strategy
# Run this from your local InterviewIQ project root directory

echo "🚀 InterviewIQ - Git Repository Setup"
echo "======================================"
echo ""

# Step 1: Initialize Git repository
echo "📁 Step 1: Initializing Git repository..."
git init
echo "✅ Git initialized"
echo ""

# Step 2: Add all files
echo "📦 Step 2: Adding all files to Git..."
git add .
echo "✅ Files staged"
echo ""

# Step 3: Create initial commit
echo "💾 Step 3: Creating initial commit..."
git commit -m "Initial commit: Project structure and documentation

- Add README.md with project overview
- Add .gitignore for Java/React monorepo
- Add documentation (Agile breakdown + prototype)
- Create placeholder backend and frontend folders
- Set up monorepo structure"
echo "✅ Initial commit created"
echo ""

# Step 4: Rename branch to main (if it's not already)
echo "🌿 Step 4: Ensuring main branch..."
git branch -M main
echo "✅ Branch renamed to 'main'"
echo ""

# Step 5: Add remote repository
echo "🔗 Step 5: Linking to GitHub remote..."
echo ""
echo "⚠️  IMPORTANT: Replace YOUR_GITHUB_USERNAME with your actual username!"
echo ""
read -p "Enter your GitHub username: " username
git remote add origin https://github.com/$username/InterviewIQ.git
echo "✅ Remote added: https://github.com/$username/InterviewIQ.git"
echo ""

# Step 6: Push to GitHub
echo "🚀 Step 6: Pushing to GitHub..."
git push -u origin main
echo "✅ Pushed to GitHub!"
echo ""

# Step 7: Create develop branch
echo "🌿 Step 7: Creating 'develop' branch..."
git checkout -b develop
git push -u origin develop
echo "✅ 'develop' branch created and pushed"
echo ""

# Step 8: Return to main branch
git checkout main
echo "✅ Switched back to 'main' branch"
echo ""

echo "🎉 SUCCESS! Your repository is set up!"
echo ""
echo "📋 What was created:"
echo "  ✓ main branch (production-ready code)"
echo "  ✓ develop branch (active development)"
echo ""
echo "📖 Next Steps:"
echo "  1. Go to GitHub and verify both branches exist"
echo "  2. Set branch protection rules (see BRANCH_PROTECTION.md)"
echo "  3. Start working on your first feature!"
echo ""
echo "💡 To start a new feature:"
echo "  git checkout develop"
echo "  git pull origin develop"
echo "  git checkout -b feature/IIQ-AUTH-01"
echo ""
