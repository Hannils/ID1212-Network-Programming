# ID1212-Network-Programming

<details>
<summary>Instructions</summary>

## Laboration 1: Socketar & Trådar

Uppgift
Uppgiften består i att skriva ett socket-baserat chatsystem med hjälp av klasserna java.net.Socket och java.net.ServerSocket. Det kan bestå av bl a föjande klasser, förslagsvis:
ChatServer: En instans av denna representerar servern. Har en tråd till var och en av de klienter som för närvarande är anslutna men också en tråd för att lyssna efter nya inkommande anslutningar från nya klienter, dessa bör representeras av en instans ClientHandler
ChatClient: En instans av denna representerar klienten. Har två trådar, en för att lyssna efter inkommande meddelanden från servern och en för att skicka meddelanden till servern.
Det finns inget krav på att hantera användarinloggning eller chattrum. Lätt och enkel. Dessa är dock krav:

Klienter ska kunna lämna chatten utan att krascha servern.
Om servern går ner ska klienterna ge ett meddelande istället för att krascha.
Både klient och server ska kunna köras på olika JVM. 
Testa att programmet verkligen fungerar genom att skapa en server och två klienter, du kommer alltså att ha tre stycken samtidiga javaprogram som körs. Samtliga program ska kunna köras från olika datorer, detta kan ni testa genom att använda t ex ssh:a mot t ex student-shell.sys.kth.se för att ansluta tillbaka till er dator.

Extrauppgift

Installera en nätverkssniffer (förslag Wireshark), spela in lite trafik från din chatt. Frågor
- Vad betyder TCP-headers i datagrammen?
- Vad betyder flaggorna ACK/SYN/SEQ/PSH/FIN?


Användbara klasser:
java.net.SocketLinks to an external site.
java.net.ServerSocketLinks to an external site.

Notera:

Om ni arbetar i par ska bara en av er ladda upp koden, annars blir det plagiatmatchning.
Ladda upp koden före (eller under) presentationen, annars kommer betyget försvinna (ny canvasinlämning kräver ny betygsättning)

## Laboration 2, En simpel HTTP-server med socket:ar

Din uppgift är att skriva ett gissningsspel med sockets där dialogen kommer att vara enligt följande (när du ansluter till din webbläsare):

https://www.csc.kth.se/~stene/guess.php

Krav på programmet: Det ska bestå av minst tre klasser:

en klass för inkommande HTTP-requests från webläsaren (Controller).
en klass för spellogiken (sessionsid för klienten, antal gissningar, hemliga talet, denna utgör Model)
en klass för att att generera HTTP-response med HTML (View).
Det ska använda java.net.Socket och java.net.ServerSocket-klasserna.
Notera:

Varje ny klient som ansluter ska leda till en ny instans av spelet ( = ett nytt spellogik-objekt) och ett "Set-Cookie"-fält lagt till i http-svaret.

Det ska vara en tråd per webbläsar-klient (går att återanvända kod från föregående laboration).

Webbläsaren skapar en ytterligare begäran om bokmärkesikonen "favicon.ico" (beroende webbläsare, testa och prova). Denna behöver ni filtrera bort på något vis.

Ett nytt webbläsarfönster (men inte flik) skapar vanligtvis en ny session (webbläsarberoende, testa och prova)

Du ska endast använda Java SE i lösningen, Java EE är ej tillåtet (det kommer i senare labb).

Extrauppgift: Använd java.net.HttpURLConnectionLinks to an external site. för att simulera en webbläsare och spela spelet 100 gånger och presentera det genomsnittliga antalet gissningar.

Notera:

Om du använder JDK 11 eller högre kan du använda java.net.http.HttpClientLinks to an external site.. klass (istället för HttpURLConnection).

Det finns inget krav på att göra en flertrådig lösning.



## Laboration 3: SSL/TLS-krypterade socket:ar

Din uppgift är att skriva ett program som ansluter till ditt @kth.se-konto, listar innehållet och sedan hämtar ett godtyckligt mejl. Du får (ännu) inte använda JavaMail (aldrig hört talas om det? bra! ) utan ska istället göra det "manuellt" enligt IMAP-protokollet. Du ska också skicka ett mail till dig själv med hjälp av SMTP-protokollet. Webbmejlen har följande konfiguration (hämtat från KTH-intranät):

Inställningar för att ta emot e-post (inkommande)
Server: webmail.kth.se
Port: 993
Protokoll: SSL/TLS
Autentisering: Normalt lösenord

Inställningar för att skicka e-post (utgående)
Server: smtp.kth.se
Port: 587
Protokoll: STARTTLS
Autentisering: Normalt lösenord

Notera:

I det första fallet (IMAP med SSL/TLS) börjar du med en krypterad session och i det andra fallet (SMTP med STARTTLS) går du över till kryptering under sessionen.
Fullständig dokumentation av IMAP finns i rfc3501Links to an external site. men för att lösa uppgiften räcker det att jämföra med en IMAP-session till exempel den här: https://en.wikipedia.org/wiki/Internet_Message_Access_ProtocolLinks to an external site.
Användbara exempel på SMTP-sessioner finns här:
https://www.samlogic.net/articles/smtp-commands-reference.htmLinks to an external site.
För att skicka användarnamn och lösenord i SMTP behöver du använda Base64-encoding och då kan https://docs.oracle.com/javase/8/docs/api/java/util/Base64.htmlLinks to an external site. vara användbart.
För att undvika att dela lösenord av olyckshändelse: Placera detta i en separat fil och läs in den till programmet.
Du behöver inget certifikat för denna deluppgift (autentiseringen sker endast med lösenord).

Krav: Ni ska kunna förklara hur publika och privata nycklar i ett ett assymmetriskt chiffer + ett symmeriskt chiffer  erbjuder en säker överföring av symmetrisk nyckel (Alice och Bob...) samt hur + kryptografiska hash ger dataintegritet.

 

Extrauppgift: Ändra nummergissningsspelet i L2 så att det använder kryptering och verifiera att en kommersiell (inte ditt eget hack) webbläsare kan ansluta (till https://localhost) och spela spelet. Eftersom det är kryptering på *servern* du ställer in, måste du ändra spelet för att använda kryptering med ett certifikat (använd nyckelverktyget keytool för att skapa ett självsignerat certifikat).


Krav: Utöver implementationsdelen (som ganska mycket följer en standardförfarande) ska du/ni även kunna förklara hur signering och verifiering med CA-fungerar.

</details>
