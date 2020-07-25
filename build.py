import git, pathlib, shutil
with git.Repo(pathlib.Path(__file__).resolve().parent) as repository:
    repository.config_writer().set_value('user', 'name', 'Your Name').release()
    repository.config_writer().set_value('user', 'email', 'you@example.com').release()
    static = 'src/main/resources/static'
    repository.git.subtree('add', '--prefix=' + static, 'https://github.com/chaowenGUO/aiohttp', 'master', '--squash')
    shutil.move(str(pathlib.Path(static).resolve().joinpath('database.sql')), pathlib.Path(static).resolve().parent)
    for _  in pathlib.Path(static).resolve().iterdir():
        if _.suffix != '.html' and _.suffix != '.js' and _.is_file(): _.unlink()
        elif _.is_dir(): shutil.rmtree(_)
