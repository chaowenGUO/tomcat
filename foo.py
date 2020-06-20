import git, pathlib
with git.Repo(pathlib.Path(__file__).resolve().parent) as repository:
    repository.config_writer().set_value('user', 'name', 'Your Name').release()
    repository.config_writer().set_value('user', 'email', 'you@example.com').release()
    repository.index.remove(['src/main/resources/static'], True, r=True)
    repository.index.commit('eve') #git commit --allow-empty-message -m ''
    repository.git.subtree('add', '--prefix=src/main/resources/static', 'https://github.com/chaowenGUO/aiohttp', 'master', '--squash')
    repository.index.move(['src/main/resources/static/database.sql', 'src/main/resources'])
    print([*pathlib.Path('src/main/resources/static').iterdir()])
            #cd src/main/resources/static
            #git rm -rf .github/workflows Procfile *.txt *.py README.md
            #git mv -f database.sql ..
            #git commit --allow-empty-message -m ''
            #git push
