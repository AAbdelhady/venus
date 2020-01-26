## Administration Guidelines

### Running Jenkins in detached mode
1. Download `jenkins.war` (currently deployed to `~` directory on __Linode__).
2. install any required command tools for building or containerization if needed (java, maven, npm, docker, etc.).
3. Run the shell command `nohup java -jar jenkins.war --httpPort=9090 &` to start jenkins on port 9090.

__Note:__ To allow jenkins to run shell commands on a remote machine, add jenkins machine to the list of trusted machines on remote host.

### Adding current machine to a remote host list of trusted machines
1. If no standard ssh key already exists under `~/.ssh` of the current machine, then generate key
using `ssh-keygen` tool.
2. Copy ssh keys to remote machine `ssh-copy-id [remote-user]@[remote-hostname|remote-ip]` 
ex. `ssh-copy-id root@172.105.69.29`.
3. Enter the password when prompted.
4. Subsequent ssh sessions will not prompt password afterwards, `ssh root@172.105.69.29`