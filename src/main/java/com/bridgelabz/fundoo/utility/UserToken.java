package com.bridgelabz.fundoo.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import com.bridgelabz.fundoo.exception.TokenException;
import com.bridgelabz.fundoo.exception.UserException;

@Component
@PropertySource("classpath:message.properties")
public class UserToken 
{
	public final String TOKEN_SECRET="fundoo";
	@Autowired
	private Environment environment;
	public String generateToken(Long id)
	{
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			String token=JWT.create()
							.withClaim("id", id)
							.sign(algorithm);
			return token;		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new TokenException(environment.getProperty("status.token.errorMessage"),Integer.parseInt(environment.getProperty("status.token.errorCode")));
		}
	}
	
	public Long tokenVerify(String token) 
	{
		Long userid;
		try {
			Verification verification=JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
			JWTVerifier jwtverifier=verification.build();
			DecodedJWT decodedjwt=jwtverifier.verify(token);
			Claim claim=decodedjwt.getClaim("id");
			userid=claim.asLong();	
		}
		catch(Exception exception)
		{
			throw new UserException("Token Not Verified",101);
		}
		
		return userid;
}
}
