#! python

import random                   # random heart rate time series
import time                     # timestaping messages
import datetime                 # formatting timestamps
import sys                      # handling interruptions
import argparse                 # for parsing command line args
import pandas                   # for data interpolation in time series
import json                     # for message formatting
import paho.mqtt.client as mqtt # for MQTT communication
import socket 

#####
## Constants Declarations for communication
#####

MQTT_TOPIC_NAME  = 'tmt_hrm'
MQTT_BROKER_PORT = 1883 

#####
## Business (simulation) logic
######

def start(identifier, client):
    """
        Start the data sending loop
    """
    print('**********')
    print('* HRM device id:   ' + identifier)
    print('* Target receiver: ' + str(client))
    print('**********')
    print(' ... press ^C to stop the device ...\n')

    start_hr = random.randint(70, 80)
    while(True):
        end_hr = send_sequence(identifier, client, start_hr)
        start_hr = end_hr


def send_sequence(id, client, start_hr):
    """
        Considering a starting heart rate value, this function creates a time series of random length to a computed target (stopped_hr). Each data is then sent 
    """
    coeff = (1 + random.uniform(-0.2, 0.2))
    stopped_hr = min(220, max(40, int(start_hr * coeff)))
    length = random.randint(5,30)
    series = pandas.Series({0: start_hr, length: stopped_hr}, 
                           index=range(0,length+1))
    s = series.interpolate(method='linear')
    s = s.apply(lambda x: round(x,2))
    for idx in range(0, length):
        timestamp = time.time()
        send_data(id, client, int(timestamp), s[idx])
        time.sleep(1)
    return stopped_hr

def send_data(id, client, timestamp, value):
    """

    """
    date = datetime.datetime.fromtimestamp(timestamp)
    print(str(date) + ': ' + str(value))
    data = { "t": timestamp, "n": id, "v": float(value), "u": "beat/min" }
    client.publish(MQTT_TOPIC_NAME, json.dumps(data), qos = 1)


#####
# MQTT Communication Layer
#####

def mqtt_init(identifier, receiver, port):
    client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2, identifier)
    client.on_publish = on_publish
    client.connect(receiver, port, 60)
    return client

def on_connect(client, userdata, flags, reason_code, properties):
    if reason_code.is_failure:
        print(f"Failed to connect: {reason_code}")
    else:
        print(f"Connection established.")

def on_publish(client, userdata, mid, reason_code, properties): 
    try:
        pass
    except KeyError:
        print("An error has occured " + reason_code)

#####
## Process command line and run the business logic
#####

def process_cli():
    """
        Process command line arguments provided by the user
    """
    parser = argparse.ArgumentParser()
    parser.add_argument("SID", help="Serial (unique) number of the HRM")
    parser.add_argument("receiver", help="Address of the receiver queue")
    args = parser.parse_args()
    return (args.SID, args.receiver)


if __name__ == "__main__":
    try:
        (sid, receiver) = process_cli()
        client = mqtt_init(sid, receiver, MQTT_BROKER_PORT)
        client.loop_start()
        start(sid, client)
    except socket.gaierror:
        print('Socket error (wrong receiver address?)')
    except ConnectionRefusedError:
        print('Connection refused (is broker alive?)')
    except KeyboardInterrupt:
        print('\n\n ... Stoping device ...\n')
        client.loop_stop()
        sys.exit(0)
