package fi.meucci;
import java.io.*;
import java.net.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class clientStr 
{
    String ricevuto;

    DataOutputStream toServer;
    Socket s1;

    public Socket connetti()
    {
        try 
        {
            s1 = new Socket("localhost", 25565);
            this.toServer  = new DataOutputStream(s1.getOutputStream());
            Messaggio m1 = new Messaggio();
            toServer.writeBytes(serializza(m1) + "\n");
        } catch (Exception e) 
        {
            System.out.println("host sconosciuto");
            System.out.println(e.getMessage());
        }
        trasmetti();
        return s1;
    }

    public void trasmetti()
    {
        try 
        {
            BufferedReader byServer = new BufferedReader(new InputStreamReader(s1.getInputStream()));
            
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("entrato");
            ricevuto=byServer.readLine();
            Messaggio mess = new Messaggio();
            mess = deserializza(ricevuto);
            for (int i = 0; i < mess.biglietti.size(); i++) 
            {
                System.out.println(mess.biglietti.get(i).ID + " " + mess.biglietti.get(i).nBiglietto);
            }
            System.out.println("scrivere l'id dei biglietti che si desidera acquistare (uno per riga) e digitare 'fine' per terminare l'inserimento");
            String k;
            Messaggio invio = new Messaggio();
            k=tastiera.readLine();
            for (;;) 
            {
                if (k.equals("fine") || k.equals("FINE") || k.equals(null)) 
                {
                    break;
                }
                else
                {
                    for (int i = 0; i < mess.biglietti.size(); i++) 
                    {
                        if (mess.biglietti.get(i).ID == Integer.parseInt(k)) 
                        {
                            invio.biglietti.add(mess.biglietti.get(i));
                        }
                    }
                }
            }
            toServer.writeBytes(serializza(invio) + "\n");
            Messaggio venduti = new Messaggio();
            venduti=deserializza(byServer.readLine());
            for (int i = 0; i < venduti.biglietti.size(); i++) 
            {
                System.out.println(venduti.biglietti.get(i).ID + " " + venduti.biglietti.get(i).nBiglietto);
            }
            toServer.writeBytes("chiudo\n");
            s1.close();
        } 
        catch (Exception e) 
        {
            
        }
    }

    public String serializza(Messaggio m1) throws Exception
    {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(m1);
    }

    public Messaggio deserializza(String mess) throws Exception
    {
        XmlMapper xmlmapper2 = new XmlMapper();
        Messaggio m2 = xmlmapper2.readValue(mess, Messaggio.class);
        return m2;
    }
}
