import git, pathlib
with git.Repo(pathlib.Path(__file__).resolve().parent) as repository:
    repository.config_writer().set_value('user', 'name', 'Your Name').release()
    repository.config_writer().set_value('user', 'email', 'you@example.com').release()
    static = 'src/main/resources/static'
    repository.index.remove([static], True, r=True)
    repository.index.commit('') #git commit --allow-empty-message -m ''
    repository.git.subtree('add', '--prefix=' + static, 'https://github.com/chaowenGUO/aiohttp', 'master', '--squash')
    repository.index.move([static + 'database.sql', str(pathlib.Path(static).resolve().parent)], f=True)
    repository.index.remove([str(_) for _  in pathlib.Path(static).iterdir() if _.suffix != '.html' and _.suffix != '.js'], True, r=True)
    repository.index.commit('')
    repository.remote().push()
