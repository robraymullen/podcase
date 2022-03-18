import { Box, List, ListItem, ListItemText } from '@mui/material';
import { Podcast, SubscribedEpisode } from '../../Types';
import EpisodeListItem from '../Episode/EpisodeListItem';
import React, { useEffect, useState } from 'react';
import {getPodcastEpisodes} from '../../services/PodcaseAPIService';
import {useParams} from "react-router-dom";

const PodcastList = (props: any) => {

    const {id} = useParams();

    const [episodes, setEpisodes] = useState<SubscribedEpisode[]>();

    useEffect(() => {
        if (id && id !== "") {
            getPodcastEpisodes(parseInt(id), setEpisodes, () => {});
        }
    }, []);

    return (
        <Box>
            {
                episodes && episodes.length > 0 ?
                    <List sx={{ bgcolor: 'background.paper' }}>
                        {
                            episodes.map((ep: SubscribedEpisode) => {
                                return <EpisodeListItem key={ep.id} episode={ep}></EpisodeListItem>
                            })
                        }
                    </List>
                    : <p>No episodes available</p>
            }
        </Box>
    );
};

export default PodcastList;
