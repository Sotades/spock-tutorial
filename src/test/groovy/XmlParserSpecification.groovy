import groovy.xml.XmlParser
import spock.lang.Specification

class XmlParserSpecification extends Specification{



    def "Should read XML file properly"() {
        given: "XML file"
        def xmlFile = getClass().getResourceAsStream("articles.xml")

        when: "Using XmlParser to read file"
        def articles = new XmlParser().parse(xmlFile)

        then: "Xml is loaded properly"
        articles.'*'.size() == 4
        articles.article[0].author.firstname.text() == "Siena"
        articles.article[2].'release-date'.text() == "2018-06-12"
        articles.article[3].title.text() == "Java 12 insights"
        articles.article.find { it.author.'@id'.text() == "3" }.author.firstname.text() == "Daniele"
    }

    def "Should add node to existing xml using NodeBuilder"() {
        given: "XML object"

        when: "Adding node to xml"
        def articleNode = new NodeBuilder().article(id: '5'){
            title('Traversing XML in a nutshell')
            author{
                firstname('Martin')
                lastname('Schmidt')
            }
            'release-date'('2019-05-18')
        }
        articles.append(articleNode)

        then: "node is added to xml properly"
        articles.'*'.size() == 5
        articles.article[4].author.lastname.text() == 'Schmidt'
    }

    def "Should modify node"() {
        given "XML  object"

    }

    void setup() {
        def xmlFile = getClass().getResourceAsStream("articles.xml")
        def articles = new XmlParser().parse(xmlFile)
    }
}
