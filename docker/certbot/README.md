# Manual certbot certificate exchange

1. create the below directory setup under root  (`mkdir /certbotsetup`)
    ```
    /certbotsetup/docker-compose.yml (copied)
                /nginx.conf (copied)
                /letsencrypt/.well-known/acme-challenge/... (token from url in cmd prompt)
    ```
2. move the content of this directory (docker-compose.yml, nginx.conf) to this new setup.
3. start manual certbot certificate exchange 
    ```bash
    certbot certonly --manual -d venusapp.ee -d www.venusapp.ee -d api.venusapp.ee
    ```
4. add files under new folder as instructed by cmd.

    example prompt:
    ```
    Create a file containing just this data:

    CD_Wj7AkR7Kpb09oJOGsx_svCy0J8x3YSbH-B11i_BY.e25paDatzJhYFPpRiFz0LsaunOrzVMzM5R_1CbI0a1w

    And make it available on your web server at this URL:

    http://www.venusapp.ee/.well-known/acme-challenge/CD_Wj7AkR7Kpb09oJOGsx_svCy0J8x3YSbH-B11i_BY

    (This must be set up in addition to the previous challenges; do not remove,
    replace, or undo the previous challenge tasks yet.)
    ```
    
    example file to create file using vim:
    ```bash
    vi /certbotsetup/letsencrypt/.well-known/acme-challenge/CD_Wj7AkR7Kpb09oJOGsx_svCy0J8x3YSbH-B11i_BY
    ```
    
    with content:
    ```bash
    CD_Wj7AkR7Kpb09oJOGsx_svCy0J8x3YSbH-B11i_BY.e25paDatzJhYFPpRiFz0LsaunOrzVMzM5R_1CbI0a1w
    ```

5. proceed with the certbot command prompt to finish the challenge and download the certificates.

    example success message:
    
    ```bash
    - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    Press Enter to Continue
    Waiting for verification...
    Cleaning up challenges
    
    IMPORTANT NOTES:
     - Congratulations! Your certificate and chain have been saved at:
       /etc/letsencrypt/live/venusapp.ee/fullchain.pem
       Your key file has been saved at:
       /etc/letsencrypt/live/venusapp.ee/privkey.pem
       Your cert will expire on 2020-04-25. To obtain a new or tweaked
       version of this certificate in the future, simply run certbot
       again. To non-interactively renew *all* of your certificates, run
       "certbot renew"
     - If you like Certbot, please consider supporting our work by:
    
       Donating to ISRG / Let's Encrypt:   https://letsencrypt.org/donate
       Donating to EFF:                    https://eff.org/donate-le
    ```

6. generate dhparams by running the command
    ```bash
    cd /etc/letsencrypt
    openssl dhparam -out ssl-dhparams.pem 2048
    ```
    
7. copy the file `options-ssl-nginx.conf` to `/etc/letsencrypt/`
