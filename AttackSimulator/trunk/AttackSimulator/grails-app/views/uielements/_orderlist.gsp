<table border="1">
    <thead>
        <tr>
            <th>
                Feed type
            </th>
            <th>
                Frequency (Comma separated 6 values for freq)(milliseconds)
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
        <g:each status="i" in="${feeds}" var="feedtype">
            <tr>
                <td>
                    <input type="checkbox" name="feedtype" style="float: left; width: 20px" value="${feedtype.feedtype}" id="${i+1}"/>${feedtype.feedtype} 
                </td>
                <td>
                    <input id="freq${i+1}" style="" type="text"  title="Example : 112,45,223,200,600,300"/>
                </td>
                <td>
                    <input id="from${i+1}" class="fromDate" type="text"  value="" title="Start Date for the feed"/>
                </td>
                <td>
                    <input id="to${i+1}" class="toDate" type="text"  value="" title="End Date for the feed"/>
                </td>
            </tr>
        </g:each>
    </tbody>
</table>
