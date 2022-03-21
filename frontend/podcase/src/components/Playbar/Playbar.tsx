import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import { Episode } from '../../Types';

interface PlaybarProps {
    currentEpisode: Episode | undefined;
}

const Playbar = ({currentEpisode}: PlaybarProps) => {
    return (
        <Box
            sx={{
                position: 'fixed',
                bottom: "0",
                width: "100%",
                height: "150px",
                backgroundColor: "rgb(50, 80, 240)",
            }}>
            <Grid container spacing={2}
                display="flex"
                alignItems="center"
                justifyContent="center"

                sx={{
                    paddingRight:"15%",
                    paddingTop:"50px",
                }}
            >
                <Grid item>
                    <audio controls>
                        {
                            currentEpisode && currentEpisode.fileUrl  &&
                            <source src={currentEpisode.fileUrl} type="audio/mp3"/>
                        }
                        
                        This browser does not support web audio.
                    </audio>
                </Grid>
            </Grid>
        </Box>
    );
};

export default Playbar;