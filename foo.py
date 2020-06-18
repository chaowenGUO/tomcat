import git
with git.Repo('.') as a
print(a.bare)
