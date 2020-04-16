from telethon.sync import TelegramClient, events
from tqdm import  tqdm
import os
import asyncio
from telethon import errors

import nest_asyncio
nest_asyncio.apply()
loop = asyncio.get_event_loop()
async def main():
    api_id = hash
    api_hash = 'hash'
    phone_number = 'domdude'
    channel_username = 'group/channel name'
        
    
    client = TelegramClient(phone_number, api_id, api_hash);
    await client.start()
    cnt = 0;
    async for message in client.iter_messages(channel_username):     
        print('Downloading' + message)
        try:
             await client.download_media(message=message)
        except errors.FloodWaitError as e:
            continue;
    
loop.run_until_complete(main())
