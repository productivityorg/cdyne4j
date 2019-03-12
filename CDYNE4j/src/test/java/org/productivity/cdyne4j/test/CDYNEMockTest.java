package org.productivity.cdyne4j.test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.productivity.cdyne4j.CDYNEClient;
import org.productivity.cdyne4j.CDYNEException;
import org.productivity.cdyne4j.CDYNERuntimeException;
import org.productivity.cdyne4j.background.BackgroundStatus;
import org.productivity.cdyne4j.background.BackgroundStatusEventHandlerIF;
import org.productivity.cdyne4j.background.debugging.DebuggingBackgroundStatusEventHandler;
import org.productivity.cdyne4j.cdyne.AbstractCDYNERq;
import org.productivity.cdyne4j.cdyne.ip2geo.ResolveIPRq;
import org.productivity.cdyne4j.cdyne.ip2geo.ResolveIPRs;
import org.productivity.cdyne4j.cdyne.ip2geo.ResolveIPRs.IPInformation;
import org.productivity.cdyne4j.cdyne.phonenotify.GetResponseCodesRq;
import org.productivity.cdyne4j.cdyne.phonenotify.GetResponseCodesRs;
import org.productivity.cdyne4j.cdyne.phonenotify.GetResponseCodesRs.Response;
import org.productivity.cdyne4j.cdyne.phonenotify.GetVoicesRq;
import org.productivity.cdyne4j.cdyne.phonenotify.GetVoicesRs;
import org.productivity.cdyne4j.cdyne.phonenotify.GetVoicesRs.Voice;
import org.productivity.cdyne4j.cdyne.phonenotify.NotifyPhoneBasicRq;
import org.productivity.cdyne4j.cdyne.phonenotify.NotifyPhoneBasicRs;
import org.productivity.cdyne4j.cdyne.phonenotify.NotifyPhoneBasicWithTransferRq;
import org.productivity.cdyne4j.cdyne.phonenotify.NotifyPhoneBasicWithTryCountRq;
import org.productivity.cdyne4j.cdyne.phoneverify.CheckPhoneNumberRq;
import org.productivity.cdyne4j.cdyne.phoneverify.CheckPhoneNumberRs;
import org.productivity.cdyne4j.cdyne.phoneverify.CheckPhoneNumberRs.PhoneReturn;
import org.productivity.cdyne4j.cdyne.phoneverify.CheckPhoneNumbersRq;
import org.productivity.cdyne4j.cdyne.phoneverify.CheckPhoneNumbersRs;
import org.productivity.cdyne4j.cdyne.smsnotify.GetMessageStatusByReferenceIDRq;
import org.productivity.cdyne4j.cdyne.smsnotify.GetMessageStatusRq;
import org.productivity.cdyne4j.cdyne.smsnotify.GetMessageStatusRs;
import org.productivity.cdyne4j.cdyne.smsnotify.GetUnreadIncomingMessagesRq;
import org.productivity.cdyne4j.cdyne.smsnotify.GetUnreadIncomingMessagesRs;
import org.productivity.cdyne4j.cdyne.smsnotify.SMSIncomingMessage;
import org.productivity.cdyne4j.cdyne.smsnotify.SMSResponse;
import org.productivity.cdyne4j.cdyne.smsnotify.SimpleSMSSendRq;
import org.productivity.cdyne4j.cdyne.smsnotify.SimpleSMSSendRs;
import org.productivity.cdyne4j.cdyne.smsnotify.SimpleSMSSendWithPostbackRq;
import org.productivity.cdyne4j.cdyne.smsnotify.advanced.AdvancedSMSSendRq;
import org.productivity.cdyne4j.cdyne.smsnotify.advanced.AdvancedSMSSendRq.MicrosoftString;
import org.productivity.cdyne4j.cdyne.smsnotify.advanced.AdvancedSMSSendRq.SMSRequest;
import org.productivity.cdyne4j.cdyne.smsnotify.advanced.AdvancedSMSSendRs;
import org.productivity.cdyne4j.networkclient.AbstractCDYNENetworkClient;
import org.productivity.cdyne4j.networkclient.stock.CDYNEStockNetworkClient;
import org.productivity.cdyne4j.test.networkclient.mock.MockCDYNENetworkClient;

/**
 * CDYNEMockTests implements mock testing for CDYNE4j.
 * 
 * <pre>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </pre>
 * 
 * @author Justin Yunke <justin-github@yunke.us>
 */
public class CDYNEMockTest {
	private CDYNEClient c = null;
	
	@BeforeClass
	public static void setupClass() throws CDYNEException {
		Assert.assertEquals(CDYNEStockNetworkClient.class,CDYNEClient.getInstance().getNetworkClient().getClass());

		AbstractCDYNENetworkClient nc = new MockCDYNENetworkClient();
		
		CDYNEClient c = CDYNEClient.getInstance();
		c.getPhoneNotify().setLicenseKey("fake-license-key");
		c.getIP2Geo().setLicenseKey("fake-license-key");
		c.getSMSNotify().setLicenseKey("fake-license-key");
		c.getPhoneVerify().setLicenseKey("fake-license-key");

		c.getPhoneNotify("test").setLicenseKey("fake-license-key");
		c.getIP2Geo("test").setLicenseKey("fake-license-key");
		c.getSMSNotify("test").setLicenseKey("fake-license-key");
		c.getPhoneVerify("test").setLicenseKey("fake-license-key");

		c.setNetworkClient(nc);
	}
	
	@Before
	public void setup() throws CDYNEException {
		this.c = CDYNEClient.getInstance();
	}		

	@After
	public void after() {
		this.c.shutdown();
	}

	@Test
	public void testSimpleObjects() {
		CDYNEException e = null;
		
		e = new CDYNEException();
		Assert.assertNull(e.getMessage());
		Assert.assertNull(e.getCause());
		
		e = new CDYNEException("test");
		Assert.assertEquals("test",e.getMessage());
		Assert.assertNull(e.getCause());
		
		e = new CDYNEException(new Exception());
		Assert.assertEquals(Exception.class.getName(),e.getMessage());
		Assert.assertEquals(Exception.class,e.getCause().getClass());
		
		e = new CDYNEException("test",new Exception());
		Assert.assertEquals("test",e.getMessage());
		Assert.assertEquals(Exception.class,e.getCause().getClass());
		
		e = new CDYNEException("test",new Exception(),true,true);
		Assert.assertEquals("test",e.getMessage());
		Assert.assertEquals(Exception.class,e.getCause().getClass());

		CDYNERuntimeException re = null;
		
		re = new CDYNERuntimeException();
		Assert.assertNull(re.getMessage());
		Assert.assertNull(re.getCause());
		
		re = new CDYNERuntimeException("test");
		Assert.assertEquals("test",re.getMessage());
		Assert.assertNull(re.getCause());
		
		re = new CDYNERuntimeException(new Exception());
		Assert.assertEquals(Exception.class.getName(),re.getMessage());
		Assert.assertEquals(Exception.class,re.getCause().getClass());
		
		re = new CDYNERuntimeException("test",new Exception());
		Assert.assertEquals("test",re.getMessage());
		Assert.assertEquals(Exception.class,re.getCause().getClass());
		
		re = new CDYNERuntimeException("test",new Exception(),true,true);
		Assert.assertEquals("test",re.getMessage());
		Assert.assertEquals(Exception.class,re.getCause().getClass());

		SMSRequest rq = new SMSRequest();
		Assert.assertNull(rq.getMessage());
		Assert.assertNull(rq.getPhoneNumbers());

		List<String> list = new ArrayList<>();
		list.add("5555552222");
		rq = new SMSRequest(list,"Test Message");
		Assert.assertEquals(1,rq.getPhoneNumbers().size());
		Assert.assertEquals("Test Message",rq.getMessage());
		List<MicrosoftString> msList = new ArrayList<>();
		msList.add(new MicrosoftString("5555552222"));
		msList.add(new MicrosoftString("5555553333"));
		rq.setPhoneNumbers(msList);
		rq.setMessage("Test Message 2");
		rq.setAssignedDID("5555551212");
		rq.setScheduledDateTime("01/01/01 01:01:01");
		rq.setStatusPostBackURL("http://example.com/postback");
		Assert.assertEquals("5555551212",rq.getAssignedDID());
		Assert.assertEquals("Test Message 2",rq.getMessage());
		Assert.assertEquals("01/01/01 01:01:01",rq.getScheduledDateTime());
		Assert.assertEquals("http://example.com/postback",rq.getStatusPostBackURL());
		Assert.assertEquals(2,rq.getPhoneNumbers().size());
		
		Properties p = new Properties();
		p.setProperty("fake","value");
		p.setProperty("http_proxy","http://127.0.0.1:3128");
		
		CDYNEStockNetworkClient nc = new CDYNEStockNetworkClient(p);
		Assert.assertNotNull(nc.getProperties());
		Assert.assertEquals(nc.getProperties().getProperty("fake"),"value");
		
		try {
			nc.initialize();
			
		} catch (Exception nce) {
			Assert.fail("Exception - " + nce.toString());
		}
		
		nc.close(null);
		nc.shutdown();
	}
	
	@Test
	public void testCDYNEClient() {
		Assert.assertFalse(CDYNEClient.getInstance().isDebug());
		CDYNEClient.getInstance().setDebug(true);
		Assert.assertTrue(CDYNEClient.getInstance().isDebug());
		
		try {
			CDYNEClient.getInstance().setNetworkClient(null);
			Assert.fail("Cannot set a null network client");
			
		} catch (CDYNERuntimeException re) {
			//
		}
	}

	@Test
	public void testGetVoices() throws CDYNEException {
		GetVoicesRq rq = new GetVoicesRq();
		Assert.assertEquals(GetVoicesRs.class,rq.getResponseClass());
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_NONE,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_PARAMS,rq.getResponseType());
		
		GetVoicesRs rs = this.c.getPhoneNotify().getVoices();
		try {
			rs.getReferenceID();
			Assert.fail("Reference ID is not implemented.");
			
		} catch (CDYNERuntimeException re) {
			//
		}
		Assert.assertNotNull(rs);
		Assert.assertTrue(rs.isSuccess());
		Assert.assertEquals(18,rs.getVoices().size());
		Voice v = rs.getVoices().get(0);
		Assert.assertNotNull(v);
		Assert.assertEquals(new Integer(0),v.getVoiceID());
		Assert.assertEquals("Diane",v.getVoiceName());
		Assert.assertEquals("Female",v.getVoiceGender());
		Assert.assertEquals(new Integer(35),v.getVoiceAge());
		Assert.assertEquals("US English",v.getVoiceLanguage());
		Assert.assertEquals("Diane - US English (Female - 35)",v.getVoiceSummary());
	}

	@Test
	public void testGetResponseCodes() throws CDYNEException {
		GetResponseCodesRq rq = new GetResponseCodesRq();
		Assert.assertEquals(GetResponseCodesRs.class,rq.getResponseClass());
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_NONE,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_PARAMS,rq.getResponseType());
		
		GetResponseCodesRs rs = this.c.getPhoneNotify().getResponseCodes();
		try {
			rs.getReferenceID();
			Assert.fail("Reference ID is not implemented.");
			
		} catch (CDYNERuntimeException re) {
			//
		}
		Assert.assertNotNull(rs);
		Assert.assertTrue(rs.isSuccess());
		Assert.assertEquals(47,rs.getResponses().size());
		Response r = rs.getResponses().get(0);
		Assert.assertNotNull(r);
		Assert.assertEquals(new Integer(0),r.getResponseCode());
		Assert.assertEquals("Queued",r.getResponseText());
	}

	@Test
	public void testIP2Geo() throws CDYNEException {
		ResolveIPRq rq = new ResolveIPRq();
		rq.setIpAddress("8.8.8.8");
		Assert.assertEquals("8.8.8.8",rq.getIpAddress());
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_PARAMS,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,rq.getResponseType());
		
		ResolveIPRs rs = this.c.getIP2Geo().send(rq);
		try {
			rs.getReferenceID();
			Assert.fail("Reference ID is not implemented.");
			
		} catch (CDYNERuntimeException re) {
			//
		}
		Assert.assertNotNull(rs);
		Assert.assertTrue(rs.isSuccess());
		IPInformation ip = rs.getIpInformation();
		Assert.assertNotNull(ip);
		Assert.assertEquals("Mountain View",ip.getCity());
		Assert.assertEquals("CA",ip.getStateProvince());
		Assert.assertEquals("United States",ip.getCountry());
		Assert.assertNull(ip.getOrganization());
		Assert.assertEquals("37.386",ip.getLatitude());
		Assert.assertEquals("-122.0838",ip.getLongitude());
		Assert.assertEquals("650",ip.getAreaCode());
		Assert.assertNull(ip.getTimeZone());
		Assert.assertFalse(ip.isHasDaylightSavings());
		Assert.assertEquals(new Integer(90),ip.getCertainty());
		Assert.assertNull(ip.getRegionName());
		Assert.assertEquals("US",ip.getCountryCode());
		Assert.assertNotNull(ip.toString());
	}
	
	protected void testCheckPhoneNumberRs(PhoneReturn pr ) {
		Assert.assertEquals("LEVEL 3 COMM - VA",pr.getCompany());
		Assert.assertTrue(pr.isValid());
		Assert.assertEquals("Assigned to a code holder for normal use.",pr.getUse());
		Assert.assertEquals("VA",pr.getState());
		Assert.assertEquals("NRFOLKZON2",pr.getRc());
		Assert.assertEquals("8825",pr.getOcn());
		Assert.assertEquals("7575449510",pr.getOriginalNumber());
		Assert.assertEquals("7575449510",pr.getCleanNumber());
		Assert.assertEquals("CHSKVAAY0MD",pr.getSwitchName());
		Assert.assertNull(pr.getSwitchType());
		Assert.assertEquals("United States",pr.getCountry());
		Assert.assertEquals("NOCLLIKNOWN",pr.getClli());
		Assert.assertEquals("CLEC - (Competitive Local Exchange Carrier)",pr.getPrefixType());
		Assert.assertEquals("252",pr.getLata());
		Assert.assertEquals("PCS - (Preferred Carrier Service)",pr.getSms());
		Assert.assertEquals("messaging.sprintpcs.com",pr.getEmail());
		Assert.assertEquals("09/26/2005",pr.getAssignDate());
		Assert.assertEquals("Norfolk",pr.getTelecomCity());
		Assert.assertEquals("Norfolk city",pr.getTelecomCounty());
		Assert.assertEquals("VA",pr.getTelecomState());
		Assert.assertEquals("23510",pr.getTelecomZip());
		Assert.assertEquals("EST",pr.getTimeZone());
		Assert.assertEquals("36.8394",pr.getLatitude());
		Assert.assertEquals("-76.3412",pr.getLongitude());
		Assert.assertTrue(pr.isWireless());
		Assert.assertEquals("7576559199",pr.getLrn());
		Assert.assertNotNull(pr.toString());
		Assert.assertNotNull(pr.toTabDelimited());
	}
	
	@Test
	public void testCheckPhoneNumber() throws CDYNEException {
		CheckPhoneNumberRq rq = new CheckPhoneNumberRq();
		rq.setPhoneNumber("7575449510");
		Assert.assertEquals("7575449510",rq.getPhoneNumber());
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_PARAMS,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,rq.getResponseType());

		CheckPhoneNumberRs rs = this.c.getPhoneVerify().send(rq);
		try {
			rs.getReferenceID();
			Assert.fail("Reference ID is not implemented.");
			
		} catch (CDYNERuntimeException re) {
			//
		}
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.isSuccess());
		Assert.assertNotNull(rs.getPhoneReturn());
		testCheckPhoneNumberRs(rs.getPhoneReturn());
	}

	@Test
	public void testCheckPhoneNumbers() throws CDYNEException {
		CheckPhoneNumbersRq rq = new CheckPhoneNumbersRq();
		rq.setPhoneNumbers("7575449510");
		Assert.assertEquals("7575449510",rq.getPhoneNumbers().get(0));
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_PARAMS,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,rq.getResponseType());

		CheckPhoneNumbersRs rs = this.c.getPhoneVerify().send(rq);
		try {
			rs.getReferenceID();
			Assert.fail("Reference ID is not implemented.");
			
		} catch (CDYNERuntimeException re) {
			//
		}
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.isSuccess());
		Assert.assertNotNull(rs.getPhoneReturns());
		Assert.assertEquals(1,rs.getPhoneReturns().size());
		testCheckPhoneNumberRs(rs.getPhoneReturns().get(0));
		Assert.assertNotNull(rs.getArrayOfPhoneReturn().toString());
	}
	
	@Test
	public void testSimpleSMSSend() throws CDYNEException {
		SimpleSMSSendRq rq = new SimpleSMSSendRq("555-555-2121","Test This");
		Assert.assertEquals("555-555-2121",rq.getPhoneNumber());
		Assert.assertEquals("Test This",rq.getMessage());

		rq = new SimpleSMSSendRq();
		rq.setPhoneNumber("555-555-1212");
		Assert.assertEquals("555-555-1212",rq.getPhoneNumber());
		rq.setMessage("Test message");
		Assert.assertEquals("Test message",rq.getMessage());
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_PARAMS,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,rq.getResponseType());
		Assert.assertEquals(SimpleSMSSendRs.class,rq.getResponseClass());
		Assert.assertNotNull(rq.toString());
		
		BackgroundStatusEventHandlerIF h = new DebuggingBackgroundStatusEventHandler();
		Assert.assertTrue(h.isDebug());
		
		SimpleSMSSendRs rs = this.c.getSMSNotify().send(rq,h);
		Assert.assertNotNull(rs);
		Assert.assertNull(rs.getReferenceID());
		SMSResponse sms = rs.getSmsResponse();
		Assert.assertNotNull(sms);
		Assert.assertFalse(sms.isCancelled());
		Assert.assertEquals("02f5fe9a-6bc3-4e72-994c-75484323c95d",sms.getMessageID());
		Assert.assertTrue(sms.isQueued());
		Assert.assertEquals("NoError",sms.getSmsError());
		Assert.assertNull(sms.getSmsIncomingMessages());
		Assert.assertFalse(sms.isSent());
		Assert.assertEquals("0001-01-01T00:00:00",sms.getSentDateTime());
		Assert.assertNotNull(rs.toString());

		SimpleSMSSendWithPostbackRq pbRq = new SimpleSMSSendWithPostbackRq();
		Assert.assertNull(pbRq.getPhoneNumber());
		Assert.assertNull(pbRq.getMessage());
		Assert.assertNull(pbRq.getStatusPostBackURL());

		pbRq = new SimpleSMSSendWithPostbackRq("5555551212","Test Message");
		pbRq.setStatusPostBackURL("http://example.com/postback/cdyne");
		Assert.assertEquals("5555551212",pbRq.getPhoneNumber());
		Assert.assertEquals("Test Message",pbRq.getMessage());
		Assert.assertEquals("http://example.com/postback/cdyne",pbRq.getStatusPostBackURL());

		pbRq = new SimpleSMSSendWithPostbackRq("5555551212","Test Message","http://example.com/postback/cdyne");
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_PARAMS,pbRq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,pbRq.getResponseType());
		Assert.assertEquals(SimpleSMSSendRs.class,pbRq.getResponseClass());
		Assert.assertNotNull(pbRq.toString());
		Assert.assertEquals("http://example.com/postback/cdyne",pbRq.getStatusPostBackURL());
		
		rs = this.c.getSMSNotify().send(pbRq);
		Assert.assertNotNull(rs);
	}

	protected void testSimpleSMSResponse(SMSResponse sms) {
		Assert.assertNotNull(sms);
		Assert.assertFalse(sms.isCancelled());
		Assert.assertEquals("02f5fe9a-6bc3-4e72-994c-75484323c95d",sms.getMessageID());
		Assert.assertFalse(sms.isQueued());
		Assert.assertEquals("NoError",sms.getSmsError());
		Assert.assertNull(sms.getSmsIncomingMessages());
		Assert.assertTrue(sms.isSent());
		Assert.assertEquals("2016-12-12T00:15:52.98",sms.getSentDateTime());
	}
	
	@Test
	public void testGetMessageStatus() throws CDYNEException {
		GetMessageStatusRq rq = new GetMessageStatusRq("02f5fe9a-6bc3-4e72-994c-75484323c95e");		
		Assert.assertEquals("02f5fe9a-6bc3-4e72-994c-75484323c95e",rq.getMessageID());
		
		rq = new GetMessageStatusRq();
		rq.setMessageID("02f5fe9a-6bc3-4e72-994c-75484323c95d");
		Assert.assertEquals("02f5fe9a-6bc3-4e72-994c-75484323c95d",rq.getMessageID());
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_PARAMS,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,rq.getResponseType());
		Assert.assertEquals(GetMessageStatusRs.class,rq.getResponseClass());
		Assert.assertNotNull(rq.toString());
		
		GetMessageStatusRs rs = this.c.getSMSNotify().send(rq);
		Assert.assertNotNull(rs);
		Assert.assertNull(rs.getReferenceID());
		SMSResponse sms = rs.getSmsResponse();
		testSimpleSMSResponse(sms);
		
		Assert.assertNotNull(rs.toString());

		GetMessageStatusByReferenceIDRq refIdRq = new GetMessageStatusByReferenceIDRq("02f5fe9a-6bc3-4e72-994c-75484323c95d");
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_PARAMS,refIdRq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,refIdRq.getResponseType());
		Assert.assertEquals(GetMessageStatusRs.class,refIdRq.getResponseClass());
		
		rs = this.c.getSMSNotify().send(refIdRq);
		Assert.assertNotNull(rs);
	}
	
	private class SimpleSMSSendBackgroundStatusEventHandler implements BackgroundStatusEventHandlerIF {
		private static final long serialVersionUID = 1L;
		public boolean completed = false;

		@Override
		public void event(BackgroundStatus status) {
			if (status.getStatusRs() instanceof GetMessageStatusRs) {
				GetMessageStatusRs rs = (GetMessageStatusRs) status.getRs();
				testSimpleSMSResponse(rs.getSmsResponse());
				if (rs.getSmsResponse().isSent()) {
					this.completed = true;
				}
			}
		}

		@Override
		public void error(BackgroundStatus status, String text) {
			//
		}

		@Override
		public void exception(BackgroundStatus status, Exception e) {
			//
		}

		@Override
		public boolean isDebug() {
			return false;
		}

		@Override
		public void debug(BackgroundStatus status, String text) {
			//
		}
	}

	@Test
	public void testSimpleSMSSendWithBackgroundStatus() throws CDYNEException {
		SimpleSMSSendRq rq = new SimpleSMSSendRq();
		rq.setPhoneNumber("555-555-1212");
		rq.setMessage("Test message");
		SimpleSMSSendBackgroundStatusEventHandler eh = new SimpleSMSSendBackgroundStatusEventHandler();
		this.c.getSMSNotify().send(rq,eh);
		CDYNEClient.getInstance().getSMSNotify().getBackgroundStatusProcessor().waitUntilQueueIsEmpty();
		int i = 0;
		while(!eh.completed && i++ < 10) {
			try { Thread.sleep(100); } catch (Exception e) { }
		}
	}
	
	@Test
	public void testGetUnreadIncomingMessages() throws CDYNEException {
		GetUnreadIncomingMessagesRq rq = new GetUnreadIncomingMessagesRq();
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_PARAMS,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,rq.getResponseType());
		Assert.assertEquals(GetUnreadIncomingMessagesRs.class,rq.getResponseClass());
		Assert.assertNotNull(rq.toString());

		GetUnreadIncomingMessagesRs rs = this.c.getSMSNotify().send(rq);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getSmsIncomingMessages());
		Assert.assertEquals(1,rs.getSmsIncomingMessages().size());
		SMSIncomingMessage im = rs.getSmsIncomingMessages().get(0);
		Assert.assertEquals("15555551212",im.getFromPhoneNumber());
		Assert.assertEquals("5b13e5b8-b128-45e3-9deb-c1f7088ac67b",im.getIncomingMessageID());
		Assert.assertEquals("a7daf4ea-51a0-4737-a04d-81ec88ee6405",im.getMatchedMessageID());
		Assert.assertEquals("Test Response",im.getMessage());
		Assert.assertEquals("2016-12-12T04:28:12.53",im.getResponseReceiveDate());
		Assert.assertEquals("15555552121",im.getToPhoneNumber());

		rs = this.c.getSMSNotify().getUnreadIncomingMessages();
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getSmsIncomingMessages());
		Assert.assertNotNull(rs.getSmsIncomingMessages().toString());
		Assert.assertEquals(1,rs.getSmsIncomingMessages().size());
	}

	@Test
	public void testAdvancedSMSSend() throws CDYNEException {
		AdvancedSMSSendRq rq = new AdvancedSMSSendRq("5555551212","Test Message");
		rq.setConcatenate(false);
		rq.setUnicode(true);
		rq.setCommonReferenceID("foo");
		
		Assert.assertFalse(rq.isConcatenate());
		Assert.assertTrue(rq.isUnicode());
		Assert.assertEquals("foo",rq.getCommonReferenceID());
		
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_XML,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,rq.getResponseType());
		Assert.assertEquals(AdvancedSMSSendRs.class,rq.getResponseClass());

		rq = new AdvancedSMSSendRq();

		List<SMSRequest> rqList = new ArrayList<>();
		rq.setRequests(rqList);
		
		SMSRequest rq1 = new SMSRequest("5555551212","Advanced SMS Test #1 Sun Dec 11 22:59:24 CST 2016");
		rq1.setReferenceID("914846a9-6b8c-4525-831e-7abc27009f52");
		rqList.add(rq1);
		
		SMSRequest rq2 = new SMSRequest("5555552121","Advanced SMS Test #2 Sun Dec 11 22:59:24 CST 2016");
		rq2.setReferenceID("914846a9-6b8c-4525-831e-7abc27009f52");
		rqList.add(rq2);

		StringWriter rqXmlSw = new StringWriter();
		rq.setLicenseKey("fake-license-key");
		rq.serializeXml(rqXmlSw);
		String rqXml = MockCDYNENetworkClient.read("AdvancedSMSSendRq.xml");
		
		Assert.assertEquals(rqXml,rqXmlSw.toString());
		
		Assert.assertNotNull(rq.toString());
		
		AdvancedSMSSendRs rs = this.c.getSMSNotify().send(rq);
		Assert.assertNotNull(rs);
		Assert.assertTrue(rs.isSuccess());
		Assert.assertNotNull(rs.getSmsResponses());
		Assert.assertEquals(2,rs.getSmsResponses().size());
		Assert.assertEquals("914846a9-6b8c-4525-831e-7abc27009f52",rs.getSmsResponses().get(0).getReferenceID());
		Assert.assertEquals("914846a9-6b8c-4525-831e-7abc27009f52",rs.getSmsResponses().get(1).getReferenceID());
	}
	
	@Test
	public void testNotifyPhoneBasic() throws CDYNEException {
		NotifyPhoneBasicRq rq = new NotifyPhoneBasicRq();
		Assert.assertNull(rq.getPhoneNumberToDial());
		Assert.assertEquals(AbstractCDYNERq.RQ_TYPE_PARAMS,rq.getRequestType());
		Assert.assertEquals(AbstractCDYNERq.RS_TYPE_XML,rq.getResponseType());
		Assert.assertEquals(NotifyPhoneBasicRs.class,rq.getResponseClass());
		
		rq = new NotifyPhoneBasicRq("5555551212","Text to Say");
		Assert.assertNull(rq.getCallerID());
		rq.setCallerID("5555552121");
		Assert.assertEquals(rq.getCallerID(),"5555552121");
		Assert.assertNull(rq.getCallerIDName());
		rq.setCallerIDName("foo");
		Assert.assertEquals(rq.getCallerIDName(),"foo");
		Assert.assertNull(rq.getVoiceID());
		rq.setVoiceID(5);
		Assert.assertEquals(rq.getVoiceID(),new Integer(5));

		rq = new NotifyPhoneBasicRq("5555551212","Text to Say",0);
		
		NotifyPhoneBasicRs rs = this.c.getPhoneNotify().send(rq);
		
		Assert.assertNotNull(rs);
		Assert.assertTrue(rs.isSuccess());
		Assert.assertEquals(new Integer(0),rs.getNotifyReturn().getResponseCode());
		Assert.assertEquals("Queued",rs.getNotifyReturn().getResponseText());
		Assert.assertFalse(rs.getNotifyReturn().isCallAnswered());
		Assert.assertEquals("1188888888",rs.getNotifyReturn().getQueueID());
		Assert.assertEquals(new Integer(0),rs.getNotifyReturn().getTryCount());
		Assert.assertFalse(rs.getNotifyReturn().isDemo());
		Assert.assertEquals(new Integer(0),rs.getNotifyReturn().getDuration());
		Assert.assertEquals("0001-01-01T01:00:00",rs.getNotifyReturn().getStartTime());
		Assert.assertEquals("0001-01-01T02:00:00",rs.getNotifyReturn().getEndTime());
		Assert.assertEquals(new Integer(0),rs.getNotifyReturn().getMinuteRate());
		Assert.assertFalse(rs.getNotifyReturn().isCallComplete());
		Assert.assertNotNull(rs.toString());
		
		NotifyPhoneBasicWithTryCountRq rqwtc = new NotifyPhoneBasicWithTryCountRq();
		Assert.assertNotNull(rqwtc);
		rqwtc = new NotifyPhoneBasicWithTryCountRq("5555551212","Test Message",3);
		NotifyPhoneBasicRs rswtc = this.c.getPhoneNotify().send(rqwtc);
		Assert.assertNotNull(rswtc);

		NotifyPhoneBasicWithTransferRq rqwtr = new NotifyPhoneBasicWithTransferRq();
		Assert.assertNotNull(rqwtr);
		rqwtr = new NotifyPhoneBasicWithTransferRq("5555551212","Test Message",3,"5555552121");
		NotifyPhoneBasicRs rswtr = this.c.getPhoneNotify().send(rqwtr);
		Assert.assertNotNull(rswtr);
	}
}
