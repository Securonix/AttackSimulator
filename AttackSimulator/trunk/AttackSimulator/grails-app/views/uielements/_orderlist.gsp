<table>
    <thead>
        <tr>
            <th>
                Feed type
            </th>
            <th>
                Frequency
            </th>
            <th>
                Date Start
            </th>
            <th>
                Date End
            </th>
        </tr>
    </thead>
    <tbody>
        <g:each status="i" in="feeds" var="feedtype">
            <tr>
                <td>
                    <input type="checkbox" name="feedtype" style="float: left; width: 20px" value="${feedtype.feedtype}" id="${i}"/>${feedtype.feedtype} 
                </td>
                <td>
                    <input id="freq${i}" style="width:30px" type="text"  title="This will be frequency of the logs sent through syslog (millisec)"/>
                </td>
                <td>
                    <input id="from${i}" class="fromDate" type="text"  value="" title="Start Date for the feed"/>
                </td>
            </tr>
        </g:each>
    </tbody>
</table>
