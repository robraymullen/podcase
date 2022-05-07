import { Box, List, CircularProgress } from '@mui/material';
import { Podcast, SubscribedEpisode } from '../../Types';
import EpisodeListItem from '../Episode/EpisodeListItem';
import { useContext, useEffect, useState } from 'react';
import { getPodcast, getPodcastEpisodes } from '../../services/PodcaseAPIService';
import { useParams } from "react-router-dom";
import Dialog from '@mui/material/Dialog';
import { AppContext } from '../../context/context';
import { changeHeaderText } from '../../context/reducer';

const PodcastList = (props: any) => {

    const { id } = useParams();

    const [episodes, setEpisodes] = useState<SubscribedEpisode[]>();
    const [description, setDescription] = useState<string>("");
    const [open, setOpen] = useState<boolean>(false); 
    const [podcast, setPodcast] = useState<Podcast>();
    const { state, dispatch } = useContext(AppContext);

    useEffect(() => {
        if (id && id !== "" && state.currentUser) {
            const setPodcastProps = (podcast: Podcast) => {
                setPodcast(podcast);
                dispatch(changeHeaderText(podcast.name));
            }
            getPodcast(parseInt(id), setPodcastProps, () => {});
            getPodcastEpisodes(state.currentUser.id, parseInt(id), setEpisodes, () => { });
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
