package com.bancvue.boot.client

import com.bancvue.boot.cm.spring.BVConfigContextLoader
import com.bancvue.boot.testsupport.ComponentTestConfig
import com.bancvue.boot.testsupport.ResourceIntegrationTestSupport
import com.bancvue.boot.widget.Widget
import com.bancvue.boot.widget.WidgetResource
import com.bancvue.boot.widget.WidgetServiceConfig
import com.bancvue.rest.client.BasicClientRequest
import com.bancvue.rest.envelope.DefaultEnvelope
import javax.ws.rs.core.GenericType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@IntegrationTest
@WebAppConfiguration
@ContextConfiguration(classes = [WidgetServiceConfig, ComponentTestConfig], loader = BVConfigContextLoader)
class CrudClientRequestComponentSpec extends Specification {

	public static final GenericType<DefaultEnvelope<Widget>> WIDGET_ENVELOPE = new GenericType<DefaultEnvelope<Widget>>() {};
	public static final GenericType<DefaultEnvelope<List<Widget>>> WIDGET_LIST_ENVELOPE = new GenericType<DefaultEnvelope<List<Widget>>>() {};

	@Autowired
	private ResourceIntegrationTestSupport resourceSupport
	@Autowired
	WidgetResource resource
	private CrudClientRequest<Widget> support

	def setup() {
		BasicClientRequest request = new BasicClientRequest(resourceSupport.resource)
		support = new CrudClientRequest<>(request, WIDGET_ENVELOPE, WIDGET_LIST_ENVELOPE).path("/root/widgets")
	}

	def cleanup() {
		resource.widgetMap.clear()
	}

	def "findMany"() {
		given:
		resource.widgetMap[1] = new Widget(1)
		resource.widgetMap[2] = new Widget(2)

		when:
		List<Widget> widgets = support.findMany()

		then:
		widgets == [new Widget(1), new Widget(2)]
	}

	def "insertWithPost"() {
		when:
		support.insertWithPost(new Widget(1))

		then:
		resource.widgetMap[1l] == new Widget(1)
		resource.widgetMap.size() == 1
	}

	def "updateWithPut"() {
		given:
		resource.widgetMap[1l] = new Widget(5)

		when:
		support.updateEntityWithPut(new Widget(1))

		then:
		resource.widgetMap[1l] == new Widget(1)
	}

	def "delete"() {
		given:
		Widget widget = new Widget(1)
		resource.widgetMap[1l] = widget

		when:
		support.path(widget).delete()

		then:
		resource.widgetMap.isEmpty()
	}

}
