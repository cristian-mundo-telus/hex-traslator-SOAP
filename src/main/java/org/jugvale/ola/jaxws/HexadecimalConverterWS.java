package org.jugvale.ola.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.awt.*;

@WebService
public class HexadecimalConverterWS {

    @WebMethod
    public String traslateHEX(@WebParam(name = "hex") String hex){
        Color color = new Color(
                Integer.valueOf(hex.substring(1,3),16),
                Integer.valueOf(hex.substring(3,5),16),
                Integer.valueOf(hex.substring(5,7), 16));

        return color.toString();

    }
}
