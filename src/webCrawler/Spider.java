package webCrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
 
public class Spider {
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
	
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}
	
	public void search(String url, String searchWord){
		while (this.pagesVisited.size() < SpiderTest.getMaxPagesToSearch()) {
			String currentUrl;
			SpiderLeg leg = new SpiderLeg();
			
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				currentUrl = this.nextUrl();
			}
			leg.crawl(currentUrl);// Lots of stuff happening here. Look at the crawl method in SpiderLeg
			boolean success = leg.searchForWord(searchWord);
			if(success) {
				SpiderTest.setResults(String.format("**Success** Word \"%s\" found at \"%s\"", searchWord, currentUrl)); 
				break;
			}
			this.pagesToVisit.addAll(leg.getLinks());
		}
		SpiderTest.setResults(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size())); 
	}
}