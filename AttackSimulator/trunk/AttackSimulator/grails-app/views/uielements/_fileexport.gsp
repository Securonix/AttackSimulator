<table border="1" id="inputRequest">
    <thead>
        <tr>
            <th></th>
            <th>
                Feed type
            </th>
            <th>
                Weekend Count
            </th>
            <th>
                Weekday Count
            </th>
            <th>
                Date Start
            </th>
            <th>
                Date End
            </th>
            <th>
                File Name Prefix
            </th>
            <th>
                Date Format
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
                    <input id="freqFile${i+1}_1" style="width:20px" type="text"  />
                </td>
                <td>
                    <input id="freqFile${i+1}_2" style="width:20px" type="text"  />
                </td>
                <td>
                    <input id="fromFile${i+1}" class="fromDate" type="text"  value="" />
                </td>
                <td>
                    <input id="toFile${i+1}" class="toDate" type="text"  value="" />
                </td>
                <td>
                    <input id="filename${i+1}" class="toDate" type="text"  value="" />
                </td>
                <td>
                    <input id="dateformat${i+1}" class="toDate" type="text"  value="" />
                </td>
            </tr>
        </g:each>
    </tbody>
</table>

<div style="float:left; width: 100%; margin: 10px"><button id="fileordersubmit" type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">Submit</button></div>