<table border="1" id="inputRequest">
    <thead>
        <tr>
            <th></th>
            <th>
                Feed type
            </th>
            <th>
                Frequency (seconds)
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
                    <input type="checkbox" name="feedtype" style="float: left; width: 20px" value="${feedtype.feedtype}" id="${i+1}"/> 
                </td>
                <td style="text-transform: uppercase">
                    ${feedtype.feedtype} 
                </td>
                <td>
                        0000-0900hrs&nbsp;<input id="freq${i+1}_1" style="width:20px" type="text"  />sec
                        0900-1300hrs&nbsp;<input id="freq${i+1}_2" style="width:20px" type="text"  />sec
                        1300-1800hrs&nbsp;<input id="freq${i+1}_3" style="width:20px" type="text"  />sec
                        1800-2000hrs&nbsp;<input id="freq${i+1}_4" style="width:20px" type="text"  />sec
                        2000-2400hrs&nbsp;<input id="freq${i+1}_5" style="width:20px" type="text"  />sec
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

<div style="float:left; width: 100%; margin: 10px"><button id="parameterssubmit" type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">Submit</button></div>
