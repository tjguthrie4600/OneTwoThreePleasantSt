from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector

from calendar_scraper.items import CalendarEvent

class CalendarSpider(BaseSpider):
  name = "calendar"
  allowed_domains = ["123pleasantstreet.com"]

  start_urls = [
    'http://123pleasantstreet.com/calendar/2012/01',
    'http://123pleasantstreet.com/calendar/2012/02',
    'http://123pleasantstreet.com/calendar/2012/03',
    'http://123pleasantstreet.com/calendar/2012/04',
    'http://123pleasantstreet.com/calendar/2012/05',
    'http://123pleasantstreet.com/calendar/2012/06',
    'http://123pleasantstreet.com/calendar/2012/07',
    'http://123pleasantstreet.com/calendar/2012/08',
    'http://123pleasantstreet.com/calendar/2012/09',
    'http://123pleasantstreet.com/calendar/2012/10',
    'http://123pleasantstreet.com/calendar/2012/11',
    'http://123pleasantstreet.com/calendar/2012/12' ]

  def parse(self, response):
    hxs = HtmlXPathSelector(response)
    calendarEntries = hxs.select('//td[@class="normal"]')
    items = []

    firstDayOfMonth = calendarEntries[0]
    firstItem = CalendarEvent()
    firstItem['dayOfMonth'] = 1
    firstItem['actName'] = firstDayOfMonth.select('center/text()').extract()[0].strip()
    firstItem['month'] = int(response.url.split('/')[-1])
 
    if firstItem['actName'] != "":
      items.append(firstItem)

    for date in calendarEntries[1:]:
      textPortions = date.select('text()').extract()
      if len(textPortions) == 0:
        continue

      cellText = textPortions[0].strip()
      if cellText == "":
        continue

      item = CalendarEvent()
      item['dayOfMonth'] = int(cellText)

      centerPortions = date.select('center/text()').extract()
      if len(centerPortions) == 0:
        continue

      if centerPortions[0].strip() == "":
        continue

      item['actName'] = centerPortions[0].strip()
      item['month'] = int(response.url.split('/')[-1])

      items.append(item)
    return items
