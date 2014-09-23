<fieldset id="${feedconfig.feedtype}" >
    <table id="attacktemplate" orderid="${orderid}" feedtype="${feedtype}" feedconfigid="${feedconfigid}">
        <tr>
            <g:each in="${tableHeaders}" var="ths">
                <th>${ths}</th>
            </g:each>
        </tr>
        <tr>
            <g:each status="i" in="${tableHeaders}" var="ths">
                <td><input type="text" orderid="${orderid}" attackelement = "attackelement${i+1}"/></td>
            </g:each>
       </tr>
    </table>
</fieldset>