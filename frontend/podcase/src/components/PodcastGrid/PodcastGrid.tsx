import Grid from '@mui/material/Grid';
import {useContext, useEffect} from 'react';
import { getUserSubscriptions, getAllPodcasts } from '../../services/PodcaseAPIService';
import { GridRoutes, Podcast } from '../../Types';
import PodcastGridItem from '../PodcastGridItem/PodcastGridItem';
import {useLocation} from "react-router-dom";
import { AppContext } from '../../context/context';
import { changeHeaderText } from '../../context/reducer';

const PodcastGrid = (props: any) => {

    const { state, dispatch } = useContext(AppContext);
    const location = useLocation();

    useEffect(() => {
        if (props && props.state === GridRoutes.PODCAST_ALL) {
            getAllPodcasts(props.setPodcasts, () => {});
        } else if (props && props.state === GridRoutes.PODCAST_SUBSCRIPTION) {
            if (state.currentUser) {
                getUserSubscriptions(state.currentUser.id, props.setPodcasts, () => {});
            }
        }
        dispatch(changeHeaderText(""));
        
    }, [location]);

    return (
            <Grid container spacing={2} key="podcastGrid">
            {
                props.podcasts.map((podcast: Podcast) => {
                    return <PodcastGridItem key={podcast.id} id={podcast.id} description={podcast.description} imageUrl={podcast.imageUrl} name={podcast.name}></PodcastGridItem>
                })
            }
            </Grid>
    )
}

export default PodcastGrid;
