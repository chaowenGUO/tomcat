import git
with git.Repo('.') as repository:
    repository.index.remove(['src/main/resources/static'], True, r=True)
#git config user.email 'you@example.com'
#            git config user.name 'Your Name'
#            git commit --allow-empty-message -m ''
#            git subtree add --prefix=src/main/resources/static https://github.com/chaowenGUO/aiohttp master --squash
#            cd src/main/resources/static
#            git rm -rf .github/workflows Procfile *.txt *.py README.md
#            git mv -f database.sql ..
#            git commit --allow-empty-message -m ''
#            git push
