<%@page import="java.util.ArrayList"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="com.feedgeneratorui.MySqlDatabase"%>

<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Securonix :: Feed Generator System Thread Management Page</title>
        <script type="text/javascript" src="./scripts/jquery.min.js"></script>
        <script type="text/javascript" src="./scripts/md5.js"></script>
        <script type="text/javascript" src="./scripts/admin.js"></script>
        <script type="text/javascript" src ="./scripts/threadManage.js"></script>
        <link rel="stylesheet" href="./styles/main.css" media="screen">
        <link rel="stylesheet" href="./styles/colors.css" media="screen">
    </head>
    <body>
        <%
        //check if the admin cookie is set.. @TODO 
            
            class TableDetails{
                public String date1;
                public String date2;
                public int transid;
                public String username;
                public String frequency;
                public String destinationip;
                public String destinationport;
                public String feedtype;
                
                TableDetails(String d1, String d2, int t, String user, String freq, String desip, String desport, String feed){
                    date1 = d1;
                    date2 = d2;
                    transid = t;
                    username = user;
                    frequency = freq;
                    destinationip = desip;
                    destinationport = desport;
                    feedtype = feed;
                }
            }
            MySqlDatabase mydb = new MySqlDatabase();
            ResultSet resultSet = mydb.getAllApprovedUsers();
            ArrayList<TableDetails> list = new ArrayList();
            if(resultSet != null){
                if(!resultSet.isBeforeFirst()){
                    //no approved users.
                }else{
                    while(resultSet.next()){
                        list.add(new TableDetails(resultSet.getString(1)+"/"+resultSet.getString(2)+"/"+ resultSet.getString(3), resultSet.getInt(4)+"/"+resultSet.getString(5)+"/"+ resultSet.getString(6),
                        resultSet.getInt(11), resultSet.getString(12), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10)));
                    }
                }
            }
            mydb.closeConnection();
        %>
        <div id="container">
            <div id="header">
                <a id="logo" href="index.jsp" title="Support Center"><img src="images/SecuronixLogo.jpeg" width="250" border=0 alt="Support Center" style="padding-top:5px; padding-left: 10px;"></a>
                <p><span>TRIAL FEED </span> REQUEST</p>
            </div>
            <div id="content">
                <div id="index">

                    <h1>Welcome to the Securonix feed generator Thread management page.</h1>


                    <div class="clear"></div>
                </div>
                <div id="Control-panel">
                    
                    <table style="border-style: solid;" align="center">
                        <tr>
                            <th>Date Start</th>
                            <th>Date Ended</th>
                            <th>OrderId</th>
                            <th>User</th>
                            <th>Frequency</th>
                            <th>Dest. IP</th>
                            <th>Dest. Port</th>
                            <th>Feed</th>
                            <th>Start Service</th>
                            <th>Stop Service</th>
                        </tr>
                        
                            <%
                            
                            for (int i = 0; i < list.size(); i++) {
                            %>
                            <tr>
                               <td>
                                  <%=list.get(i).date1%>
                               </td>
                               <td>
                                  <%=list.get(i).date2%>
                               </td>
                               <td>
                                  <%=list.get(i).transid%>
                               </td>
                               <td>
                                  <%=list.get(i).username%>
                               </td>
                               <td>
                                  <%=list.get(i).frequency%>
                               </td>
                               <td>
                                  <%=list.get(i).destinationip%>
                               </td>
                               <td>
                                  <%=list.get(i).destinationport%>
                               </td>
                               <td>
                                  <%=list.get(i).feedtype%>
                               </td>
                               <td >
                                   <button id="<%=list.get(i).transid%>" service-type="startthread">Start Service </button>
                               </td>
                               <td >
                                   <button id="<%=list.get(i).transid%>" service-type="stopthread">Stop Service </button>
                               </td>
                            </tr>
                         <% } %>
                    </table>
                    
                    <div id="messages"></div>

                    <div style="clear:both; text-align: center; color: red; text-decoration: bold; font-size: 15px;" id="message"></div> 
                </div>
                <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
                <div align="center">
                    <a id="powered_by" href="http://securonix.com"><img src="./images/osticket-securonixonly.gif" width="126" height="23" alt="Powered by osTicket"></a>
                </div>
            </div>
    </body>
</html>
