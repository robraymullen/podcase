import { Box, List, ListItem, ListItemText, CircularProgress } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { DeadDownload } from '../../Types';
import { getDeadDownloads } from '../../services/PodcaseAPIService';
import DeadDownloadListItem from './DeadDownloadListItem';

const DeadDownloadTab = () => {

    const [deadDownloads, setDeadDownloads] = useState<DeadDownload[]>();

    useEffect(() => {
        getDeadDownloads(setDeadDownloads, (exception: any) => {console.error(exception);})
    }, []);

    return (
        <Box>
            {
                deadDownloads && deadDownloads.length > 0 ?
                    <div>
                        <List sx={{ bgcolor: 'background.paper' }}>
                            {
                                deadDownloads.map((download: DeadDownload) => {
                                    return <DeadDownloadListItem key={download.id+""} download={download}>
                                    </DeadDownloadListItem>
                                })
                            }
                        </List>
                    </div>

                    : <CircularProgress />
            }
        </Box>
    )
};

export default DeadDownloadTab;