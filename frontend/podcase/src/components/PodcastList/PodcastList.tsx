import { Box, List, ListItem, ListItemText, CircularProgress } from '@mui/material';
import { Podcast, SubscribedEpisode } from '../../Types';
import EpisodeListItem from '../Episode/EpisodeListItem';
import React, { useEffect, useState } from 'react';
import { getPodcast, getPodcastEpisodes } from '../../services/PodcaseAPIService';
import { useParams } from "react-router-dom";
import Dialog from '@mui/material/Dialog';

const PodcastList = (props: any) => {

    const { id } = useParams();

    const [episodes, setEpisodes] = useState<SubscribedEpisode[]>();
    const [description, setDescription] = useState<string>("");
    const [open, setOpen] = useState<boolean>(false); 
    const [podcast, setPodcast] = useState<Podcast>();

    useEffect(() => {
        if (id && id !== "") {
            const setPodcastProps = (podcast: Podcast) => {
                setPodcast(podcast);
                props.setHeaderText(podcast.name);
            }
            getPodcast(parseInt(id), setPodcastProps, () => {});
            getPodcastEpisodes(parseInt(id), setEpisodes, () => { });
        }
    }, []);

    return (
        <Box>
            {
                podcast && episodes && episodes.length > 0 ?
                    <div>
                        <Dialog open={open} onClose={() => {setOpen(false);}}>
                            {description}
                        </Dialog>
                        <List sx={{ bgcolor: 'background.paper' }}>
                            {
                                episodes.map((ep: SubscribedEpisode) => {
                                    return <EpisodeListItem key={ep.guid} episode={ep} 
                                    setCurrentEpisode={props.setCurrentEpisode} 
                                    setDialogDescription={setDescription} 
                                    setDialogOpen={setOpen}
                                    imageUrl={podcast.imageUrl}
                                    >                                        
                                    </EpisodeListItem>
                                })
                            }
                        </List>
                    </div>

                    : <CircularProgress />
            }
        </Box>
    );
};

export default PodcastList;
