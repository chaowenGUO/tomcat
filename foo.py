import git, pathlib
with git.Repo(pathlib.Path(__file__).resolve().parent) as repository:
    #repository.config_writer().set_value('user', 'name', 'Your Name').release()
    #repository.config_writer().set_value('user', 'email', 'you@example.com').release()
    repository.index.remove(['src/main/resources/static'], True, r=True)
    repository.index.commit('') #git commit --allow-empty-message -m ''
    repository.git.subtree('add', '--prefix=src/main/resources/static', 'https://github.com/chaowenGUO/aiohttp', 'master', '--squash')
    repository.index.move(['src/main/resources/static/database.sql', 'src/main/resources'], f=True)
    repository.index.remove([str(_) for _  in pathlib.Path('src/main/resources/static').iterdir() if _.suffix != '.html' and _.suffix != '.js'], True, r=True)
    #repository.index.commit('')
    repository.remote().push()
