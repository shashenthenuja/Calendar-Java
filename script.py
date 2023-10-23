# Script to add events 
print("Starting script...")
from java.util import HashMap
from java.time import LocalDateTime
from java.time.format import DateTimeFormatter
from edu.curtin.assignment2.pluginapi import Event


holidays = {
    "Christmas Day": "2023-12-25",
    "Christmas Eve": "2023-12-24",
    "New Year's Day": "2024-01-01",
    "Halloween": "2023-10-31",
    "Thanksgiving": "2023-11-23",
    "Easter": "2024-04-12",
    "Independence Day": "2023-02-04",
    "Good Friday": "2023-04-07",
    "Sinhala and Tamil New Year": "2023-04-14",
    "May Day": "2024-05-01",
    "Vesak Poya": "2024-05-06",
    "Poson Poya": "2024-06-05",
}

# create a new event from the given arguments
def creatEvent(args):
    list = api.getEvents()
    list.add(Event(args.get("title"), set_date(args.get("startDate")), 0))
    
# set the date using java LocalDateTime
def set_date(date):
    date_string = date
    time_string = "00:00:00"
    
    date_time_string = date_string + "T" + time_string
    formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return LocalDateTime.parse(date_time_string, formatter)
    

# for every holiday create a new event that stores in the api list
for title, start_date in holidays.items():
    args = HashMap()
    args.put("title", title)
    args.put("startDate", start_date)
    args.put("repeat", "0")

    creatEvent(args)