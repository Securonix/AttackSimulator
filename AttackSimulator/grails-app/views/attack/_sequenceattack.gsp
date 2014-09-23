<g:each status="i" in="${attackdefinition}" var="atkdef">
    <div id="${atkdef.orderid}" style="float: left; width: 100%; padding: 20px"><p style="float: left; width: 100%; text-align: left; text-transform: none">Feed type: "${atkdef.feedtype}" | Order id: "${atkdef.orderid}" | Attack elements: "${attackelements[i]}"</p><p style="float:left; width: 100%; text-align: left; text-transform: none">Step: <input type="text"/> &nbsp; Next Step: <input type="text"/></p></div>
</g:each>
