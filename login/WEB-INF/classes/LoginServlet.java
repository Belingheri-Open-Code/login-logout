import java.io.IOException;  
import java.io.PrintWriter;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;
import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;  
public class LoginServlet extends HttpServlet {  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                    throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
        request.getRequestDispatcher("link.html").include(request, response);  
        
        
    try{
        File fXmlFile = new File(".\\webapps\\login\\WEB-INF\\classes\\utenti.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile); 
        NodeList nList = doc.getElementsByTagName("user");

        doc.getDocumentElement().normalize();
        
        String name=request.getParameter("name");  
        String password=request.getParameter("password");  
         //
        boolean t=false;
         for (int temp = 0; temp < nList.getLength()&&t==false; temp++) {

        Node nNode = nList.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
              Element eElement = (Element) nNode;  
              if(password.equals(eElement.getElementsByTagName("pass").item(0).getTextContent())&&name.equals(eElement.getElementsByTagName("name").item(0).getTextContent())){  
                      out.print("Benvenuto, "+name);  
                      HttpSession session=request.getSession();  
                      session.setAttribute("name",name);
                      t=true;
                }
        }
        
    }
         //
        if(!t){  
         
            out.print("Sorry, username or password error!");  
            request.getRequestDispatcher("login.html").include(request, response);  
        }  
        out.close();  
        }
catch(Throwable e){
   // out.print(e.getMessage());
    e.printStackTrace();
}
    }  
}  