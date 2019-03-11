## Synopsis

CDYNE4j is a Java library for accessing several APIs provided by the vendor CDYNE.com.

Currently supported APIs include:

* SMS Notify - http://cdyne.com/api/phone/sms/
* Phone Verify - http://cdyne.com/api/phone-verification/
* Phone Notify - http://cdyne.com/api/phone/notify/
* IP2GEO - http://cdyne.com/api/ip-location/

Advanced functionality includes:

* Separate Thread "polling" capability for checking on status of SMS messages
* Support for multiple profiles (license keys)
* Support for Internet proxies
* JUnit tests with mock data (no CDYNE licenses/communication needed) supporting more than 75% code coverage

## Getting Started

Before using this library, you will need to acquire license keys from CDYNE.com.

Test licenses which support limited functionality are available immediately by registering here:  https://secure.cdyne.com/support/register.aspx

Contact CDYNE.com for more information on pricing on use of their APIs.

## Getting Started:  Prerequisites

CDYNE4j requires Oracle Java 8 or higher.  No third party JARs are required for default functionality.

## Getting Started:  Setting Your License Keys

Licenses are required to use CDYNE's APIs.

Execute one or more of the following in the bootstrap (one-time run at startup) code of your application:

CDYNEClient.getInstance().getSMSNotify().setLicenseKey("_put_license_key_here_");
CDYNEClient.getInstance().getPhoneNotify().setLicenseKey("_put_license_key_here_");
CDYNEClient.getInstance().getPhoneVerify().setLicenseKey("_put_license_key_here_");
CDYNEClient.getInstance().getIP2Geo().setLicenseKey("_put_license_key_here_");

## Getting Started:  Sending an SMS Message

SimpleSMSSendRq rq = new SimpleSMSSendRq();
rq.setPhoneNumber("_put_cell_number_here_");
rq.setMessage("_put_text_message_here");

SimpleSMSSendRq rs = CDYNEClient.getInstance().getSMSNotify().send(rq);
System.out.println(rs.isSuccess());

## Getting Started:  Sending a Phone Notify Message

NotifyPhoneBasicRq rq = new NotifyPhoneBasicRq();
rq.setPhoneNumberToDial("_put_phone_number_here_");
rq.setTextToSay("A voice test.");
rq.setCallerID("_put_caller_id_number_here_");
rq.setCallerIDName("_put_caller_id_name_here_");
rq.setVoiceID(3);
		
NotifyPhoneBasicRs rs = CDYNEClient.getInstance().getPhoneNotify().send(rq);
System.out.println(rs.isSuccess());
System.out.println(rs.getReferenceID());

## License

This library utilizes the Apache License Version 2.0.  See "LICENSE" for a copy or consult: http://apache.org/licenses/LICENSE-2.0.html
