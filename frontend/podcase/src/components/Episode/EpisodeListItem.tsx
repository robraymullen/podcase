import { SubscribedEpisode } from "../../Types";
import { ListItem, ListItemText, ListItemAvatar, Avatar, Grid } from '@mui/material';
import Typography from "@mui/material/Typography";
import Box from '@mui/material/Box';
import PlayCircleFilledIcon from '@mui/icons-material/PlayCircleFilled';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import HeadphonesIcon from '@mui/icons-material/Headphones';
import CircularProgress, {
    CircularProgressProps,
} from '@mui/material/CircularProgress';

const EpisodeListItem = ({ episode }: { episode: SubscribedEpisode }) => {

    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    const pubDate = new Date(episode.publication_date);
    const percentPlayed = ((episode.play_length ? episode.play_length : 0) / episode.duration) * 100;
    const percentageProps: CircularProgressProps & { value: number } = { value: percentPlayed };

    return (
        <ListItem sx={{ border: 1, borderColor: 'primary.main', marginBottom: "1%" }}>
            <Grid container spacing={2}>
                <Grid item>
                    <Box
                        position="relative"
                        alignItems="center"
                        justifyContent="center"
                    >
                        <ListItemAvatar>
                            <img src={episode.image_url} height="40" width="40" />
                        </ListItemAvatar>
                    </Box>
                </Grid>
                <Grid item xs={12} sm container>
                    <Grid item xs container direction="column" spacing={2}>
                        <Grid item xs>
                            <ListItemText  >
                                <Typography
                                    sx={{ display: 'inline' }}
                                    component="span"
                                    variant="body2"
                                    color="text.primary"
                                >
                                    {episode.title}
                                </Typography>
                            </ListItemText>
                        </Grid>
                        <Grid item>
                            <Box>
                                <Box>
                                    <CalendarMonthIcon></CalendarMonthIcon>
                                    <Typography variant="caption">
                                        {pubDate.toDateString()}
                                    </Typography>
                                </Box>
                                <Box>
                                    <HeadphonesIcon />
                                    <Typography variant="caption">
                                        {
                                            new Date(episode.duration * 1000).toISOString().substr(11, 8)
                                        }
                                    </Typography>
                                </Box>
                                <Box sx={{ position: 'relative', display: 'inline-flex' }} >
                                    <CircularProgress variant="determinate" {...percentageProps} size={25}/>
                                    <Box
                                        sx={{
                                            top: 0,
                                            left: 0,
                                            bottom: 0,
                                            right: 0,
                                            position: 'absolute',
                                            display: 'flex',
                                            alignItems: 'center',
                                            justifyContent: 'center',
                                        }}
                                    >
                                        <Typography
                                            variant="caption"
                                            component="div"
                                            color="text.secondary"
                                            fontSize="0.5rem"
                                        >{`${Math.round(percentageProps.value)}%`}</Typography>
                                    </Box>
                                </Box>
                                <PlayCircleFilledIcon />
                            </Box>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </ListItem>


    )
};

export default EpisodeListItem;