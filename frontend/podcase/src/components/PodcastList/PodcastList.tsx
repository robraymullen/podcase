import { Box, List, ListItem, ListItemText, CircularProgress } from '@mui/material';
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
                                return <EpisodeListItem key={ep.guid} episode={ep} setCurrentEpisode={props.setCurrentEpisode}></EpisodeListItem>
                            })
                        }
                    </List>
                    : <CircularProgress />
            }
        </Box>
    );
};

export default PodcastList;
