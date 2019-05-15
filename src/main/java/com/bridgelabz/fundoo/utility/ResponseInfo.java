package com.bridgelabz.fundoo.utility;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.UserDTO;

public class ResponseInfo {
/**
 *  Purpose : This method set statusMessage and statusCode and return response object.
 * @param statusCode
 * @param statusMessage
 * @return
 */
public static Response getResponse(int statusCode, String statusMessage ) 
{
	Response response = new Response();
	response.setStatusCode(statusCode);
	response.setStatusMessage(statusMessage);
	return response;
}

/**
 *  Purpose : This method set statusMessage,statusCode,token and return response object
 * @param statusCode
 * @param statusMessage
 * @return
 */
public static ResponseToken tokenStatusInfo(int statusCode, String statusMessage, String token,UserDTO userDto)
{   ResponseToken response = new ResponseToken();
    response.setStatuscode(statusCode);
    response.setStatusmessage(statusMessage);
    response.setToken(token);
    response.setUserDto(userDto);
    
	return response;
}

}
