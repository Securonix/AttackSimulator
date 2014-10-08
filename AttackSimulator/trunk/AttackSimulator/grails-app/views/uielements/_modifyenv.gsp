<div id="index">
    <p class="big">Describe your Environment. We will pick randomized users for you and assign them ips based on your choice of 
        IP range</p>
    <hr />
    <br />
    <div id="form-fields">
        <form id=payment method="POST">
            <fieldset>
                <legend>Number of Users in your Organization</legend>
                <ol>
                    <li>
                        <label for=name>Number of Users</label>
                        <input id="name" name="name" type="number" placeholder="1-665" required autofocus>
                    </li>
                </ol>
            </fieldset>
            <fieldset>
                <legend>Network Information for your organization</legend>
                <ol>
                    <li>
                        <label for=postcode>Internal IP Address Ranges</label>
                        <select name = "internal" id = "internalranges">
                            <option value="classa">Class A (0.0.0.0 - 127.255.255.255)</option>
                            <option value="classb">Class B (128.0.0.0 - 191.255.255.255)</option>
                            <option value="classc">Class C (192.0.0.0 - 223.255.255.255)</option>
                        </select>
                    </li>
                    .<li>
                        <label for=postcode>DMZ IP Address Ranges</label>
                        <select name = "dmz" id="dmzranges">
                            <option value="classb">Class B (128.0.0.0 - 191.255.255.255)</option>
                            <option value="classc">Class C (192.0.0.0 - 223.255.255.255)</option>
                        </select>
                    </li>
                    <li>
                        <label for=country>External IP address ranges</label>
                        <g:select id = "type" name="country" value="${countries}" noSelection="${['null':'Select Countries (press ctrl to select multiple)...']}"
                        from="${countries}"></g:select>
                        </li>
                    </ol>
                </fieldset>
                <fieldset>
                    <button type=submit>Submit</button>
                </fieldset>
            </form>
        </div>

    <div class="clear"></div>
</div>