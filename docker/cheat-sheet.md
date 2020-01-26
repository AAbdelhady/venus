### Firewall
list open ports and service => `sudo firewall-cmd --list-all`
open service or port (ex. http ~ port 80) => `sudo firewall-cmd --add-service=http --permanent`
close service or port (ex. https ~ port 443) => `sudo firewall-cmd --remove-service=https --permanent`
reload => `sudo firewall-cmd --reload`