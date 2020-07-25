import git, pathlib, shutil
with git.Repo(pathlib.Path(__file__).resolve().parent) as repository:
    repository.config_writer().set_value('user', 'name', 'Your Name').release()
    repository.config_writer().set_value('user', 'email', 'you@example.com').release()
    static = pathlib.Path('src/main/resources/static').resolve()
    repository.git.subtree('add', '--prefix=' + str(static), 'https://github.com/chaowenGUO/aiohttp', 'master', '--squash')
    shutil.move(str(static / 'database.sql'), static.parent)
    for _  in static.iterdir():
        if _.suffix != '.html' and _.suffix != '.js' and _.is_file(): _.unlink()
        elif _.is_dir(): shutil.rmtree(_)
