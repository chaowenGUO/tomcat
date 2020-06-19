import git
with git.Repo('.') as repository:
    repository.config_writer().set_value('user', 'name', 'Your Name').release()
    repository.config_writer().set_value('user', 'email', 'you@example.com').release()
    repository.index.remove(['src/main/resources/static'], True, r=True)
    repository.index.commit('') #git commit --allow-empty-message -m ''
    #git subtree add --prefix=src/main/resources/static https://github.com/chaowenGUO/aiohttp master --squash
            #cd src/main/resources/static
            #git rm -rf .github/workflows Procfile *.txt *.py README.md
            #git mv -f database.sql ..
            #git commit --allow-empty-message -m ''
            #git push
