import git, pathlib, shutil
with git.Repo(pathlib.Path(__file__).resolve().parent) as repository:
    repository.config_writer().set_value('user', 'name', 'Your Name').release()
    repository.config_writer().set_value('user', 'email', 'you@example.com').release()
    static = pathlib.Path('static')
    repository.git.subtree('add', '--prefix=' + str(static), 'https://github.com/chaowenGUO/aiohttp', 'master', '--squash')
    static = static.resolve()
    for _  in static.iterdir():
        if _.suffix == '.html' or _.suffix == '.js' or _.suffix == '.sql': shutil.move(str(_), pathlib.Path('src/main'))
    shutil.rmtree(static)
