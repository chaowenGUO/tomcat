import git, pathlib, shutil
with git.Repo(pathlib.Path(__file__).resolve().parent) as repository:
    repository.config_writer().set_value('user', 'name', 'Your Name').release()
    repository.config_writer().set_value('user', 'email', 'you@example.com').release()
    static = 'src/main/resources/static'
    repository.git.subtree('add', '--prefix=' + static, 'https://github.com/chaowenGUO/aiohttp', 'master', '--squash')
    repository.index.move([static + '/database.sql', str(pathlib.Path(static).resolve().parent)], f=True)    
    for _  in pathlib.Path(static).iterdir():
        if _.suffix != '.html' and _.suffix != '.js' and _.is_file(): _.unlink()
        else if _.is_dir(): shutil.rmtree(_)
