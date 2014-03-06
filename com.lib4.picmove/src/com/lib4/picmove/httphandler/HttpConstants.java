package com.lib4.picmove.httphandler;

public class HttpConstants {

	// General/Common Request Header Json key

	/*
	 * ############################## SIGN IN REQUEST PARAMS/JSON KEYS
	 */
	public static final String PASSWORD_JKEY = "password";
	public static final String TYPE_JKEY = "consumer";
	
	public static final String SUCCESS_JKEY = "success";

	/*
	 * ############################## SIGN IN RESPONSE JSON KEYS
	 */
	public static final String EMAILADDRES = "emailAddress";
	public static final String PASSWORD = "password";
	public static final String SIGNIN_REQUEST_KEY = "signInRequest";

	/*
	 * ########################### PROFILE RESPONSE JSON KEYS
	 */

	public static final String MESSAGE_JKEY = "message";
	public static final String STATUS_JKEY = "status";
	public static final String PROFILE_JKEY = "Profile";
	public static final String USERID_JKEY = "userId";
	public static final String EMAILADDRESS_JKEY = "emailAddress";
	public static final String ADDRESS_JKEY = "address";
	public static final String PHONENUMBER_JKEY = "phoneNumber";
	public static final String EMAILVERIFIED_JKEY = "isEmailVerified";
	public static final String PHONEVERIFIED_JKEY = "isPhoneVerified";
	public static final String NAME_JKEY = "name";

	
	
	/*
	 * ####################  GET MY MOVES RESPONSE JSON KEYS
	 * 
	 */
	public static final String GETMYMOVES_REQUEST_KEY = "getMyMoveRequest";
	public static final String MOVES_STATUS_KEY = "moveStatus";
	
	
	
	public static final String RESPONSE_JKEY		=	"response";
	public static final String LARGEBOX_COUNT_JKEY	=	"largeBoxCount";
	public static final String REJECT_REASON_JKEY	=	"rejectReason";
	
	
	public static final String MOVE_REQUEST_JKEY	=	"moveRequest";
	
	
	public static final String DISPATCH_DATE_JKEY	=	"dispatchDate";
	public static final String BIGITEMPRESENT_JKEY	=	"isBigItemsPresent";
	public static final String MOVESTATUS_JKEY	=	"moveStatus";
	public static final String BIGITEMS_JKEY	=	"bigItems";
	
	public static final String SMALLBOX_COUNT_JKEY	=	"smallBoxCount";
	public static final String CONDITIONS_JKEY	=	"conditions";
	public static final String MMOVIEID_JKEY	=	"moveId";
	public static final String PRICE_QUOTE_JKEY	=	"priceQuote";
	public static final String MEDIUMBOX_COUNT_JKEY	=	"mediumBoxCount";
	public static final String ITEMWONTFITBOX_COUNT_JKEY	=	"itemWontFitInBox";
	public static final String SOURCEADDRESS_JKEY	=	"sourceAddress";
	public static final String FITIN_ELEVATOR_JKEY	=	"fitInElevator";
	public static final String DISASSEMBLY_JKEY	=	"disassembly";

	public static final String EXPECTED_RECEIVEDDATE_JKEY	=	"expectedReceivedDate";
	public static final String DESTINATION_ADDESS_JKEY	=	"destinationAddress";

	
	
	
	/*
	 * ############################ BOX Items
	 */
	
	
	
	public static final String ITEMNAME_JKEY	=	"itemName";
	public static final String ITEM_DESCRIPTION_JKEY	=	"itemDescription";
	public static final String MOVEID_JKEY	=	"moveId";
	public static final String BIGITEM_ID_JKEY	=	"bigItemId";
	public static final String REQUIRES_DISASSEMBLY_JKEY	=	"requiresDisassembly";
	public static final String DOES_FITIN_ELEVATOR_JKEY	=	"doesFitInElevator";
	public static final String ITEM_URL_JKEY	=	"itemUrl";
	public static final String ITEMIMAGE_JKEY	=	"image";
	
	
	
	
	/*
	 *########################## SignUp  
	 * 
	 */
	
	
	public static final String SIGNUP_REQUEST	=	"signUpRequest";
	
	public static final String UPDATE_PROFILE_REQUEST	=	"updateProfileRequest";
	
	public static final String ACCEPT_REJECT_MOVEDEALREQUEST	=	"accrejMoveDealRequest";
	
	
	
}
