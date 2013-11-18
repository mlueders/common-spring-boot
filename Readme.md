This project acts as a bootstrap for generic bancvue projects, setting up the gradle wrapper, the standard .gitignore and a simple gradle build file.
When creating a new project, follow the steps below...

First, create a repository in Stash for the target project.

Next, initialize the project

    git init
    git remote add build http://git.bvops.net/scm/com/generic-bootstrap.git
    git fetch build
    git merge build/master

Next, update gradle.properties and set the artifactId

Finally, set the remote and push to master

    git remote add origin {project stash url}
    git push origin master
