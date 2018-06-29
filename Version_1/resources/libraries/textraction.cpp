//This file is used to generate text.exe which handles the text module of Tacgen

#include <opencv2/core/utility.hpp>
#include "opencv2/imgproc.hpp"
#include "opencv2/imgcodecs.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/imgcodecs.hpp"
#include "opencv2/opencv.hpp"
#include <iostream>
#include <vector>
#include <cstdlib>
#include<algorithm>
#include<math.h>
#include<string>
#include<sstream>
#include<fstream>
#include<climits>

#define PI 3.14159265
using namespace cv;
using namespace std;

Size size(50,50);
//--------------------------------function------------------------------------//
void imadjust(const Mat& src, Mat& dst, int tol = 1, Vec2i in = Vec2i(0, 255), Vec2i out = Vec2i(0, 255)){
    dst = src.clone();
    tol = max(0, min(100, tol));
    if (tol > 0){
        // Compute in and out limits
        // Histogram
        vector<int> hist(256, 0);
        for (int r = 0; r < src.rows; ++r){
            for (int c = 0; c < src.cols; ++c){
                hist[(int)src.at<uchar>(r,c)]++;
            }
        }
        // Cumulative histogram
        vector<int> cum = hist;
        for (int i = 1; i < hist.size(); ++i) {
            cum[i] = cum[i - 1] + hist[i];
        }
        // Compute bounds
        int total = src.rows * src.cols;
        int low_bound = total * tol / 100;
        int upp_bound = total * (100-tol) / 100;
        in[0] = distance(cum.begin(), lower_bound(cum.begin(), cum.end(), low_bound));
        in[1] = distance(cum.begin(), lower_bound(cum.begin(), cum.end(), upp_bound));
    }

    // Stretching
    float scale = float(out[1] - out[0]) / float(in[1] - in[0]);
    for (int r = 0; r < dst.rows; ++r){
        for (int c = 0; c < dst.cols; ++c){
            int vs = max((int)src.at<uchar>(r, c) - in[0], 0);
            int vd = min(int(vs * scale + 0.5f) + out[0], out[1]);
			if((int)src.at<uchar>(r,c)>220)
				vd=255;
			else
				vd=(int)src.at<uchar>(r,c);
	            dst.at<uchar>(r, c) = saturate_cast<uchar>(vd);
        }
    }
}

//--------------------------------function------------------------------------//
void pushBB2file(vector<Rect> temp,vector<string> str,char * imageName){
	string st(imageName);
    st = st.substr(st.find_last_of("/")+1);
	FILE *fptr1;
    string h = st.append(".txt");
	const char * g = h.c_str();
    fptr1=fopen(g,"w"); 

	for (int i =0;i<temp.size();i++){
		if(str[i].compare("")==0){continue;}
		fprintf(fptr1,"%d,%d,%d,%d-%s\n",temp[i].x,temp[i].y,temp[i].width,temp[i].height,str[i].c_str());
	}
	fclose(fptr1);
}

//--------------------------------function------------------------------------//
float distance(float x1, float y1, float x2 , float y2){
	return sqrt( pow(x2-x1,2) + pow(y2-y1,2) );
}

float realdistance(Rect a, Rect b,Point a1 , Point b1){
	bool left,right,bottom,top;
	float x1,x2,x1b,x2b,y1,y2,y1b,y2b;
	x1=a.x;
	x1b=a.x+a.width;
	x2=b.x;
	x2b=b.x+b.width;
	y1=a.y;
	y1b=a.y+a.width;
	y2=b.y;
	y2b=b.y+b.width;

	left = x2b < x1;
	right = x1b < x2;
	bottom = y2b < y1;
	top = y1b < y2;

	if (top && left )
		return distance(x1,y1b,x2b,y2);
	else if(left && bottom)
		return distance(x1,y1,x2b,y2b);
	else if(right && bottom)
		return distance(x1b,y1,x2,y2b);
	else if(right && top)
		return distance(x1b,y1b,x2,y2);	
}

//--------------------------------function------------------------------------//
float angle(float x1, float y1, float x2 , float y2){
	return atan2( (y2-y1), (x2-x1) ) * 180 /PI ;
}

//--------------------------------function------------------------------------//
Rect LinkBB ( Rect a, Rect b)
{
	Rect linked;
	if(a.x<=b.x)
	{
		linked.x=a.x;
		if(a.x+a.width < b.x+b.width)
		linked.width=b.x-a.x+b.width;
		else
		linked.width=a.width;
	}
	else
	{
		linked.x=b.x;
		if(b.x+b.width<a.x+a.width)
		linked.width=a.x-b.x+a.width;
		else
		linked.width=b.width;
	}

	if(a.y<=b.y){
		linked.y=a.y;	
		if(a.y+a.height < b.y+b.height)
		linked.height=b.y-a.y+b.height;
		else
		linked.height=a.height;
	}
	else{
		linked.y=b.y;
		if(b.y+b.height < a.y+a.height)
		linked.height=a.y-b.y+a.height;
		else
		linked.height=b.height;
	}
	return linked;
}

//--------------------------------function------------------------------------//

void indexmatch(int *VisitStatus,int size,int old_index,int new_index){
	for (int i=0;i<size;i++)
		if (VisitStatus[i]==old_index)
			VisitStatus[i]=new_index;
}

//--------------------------------function------------------------------------//
vector<int> LineGenerate(vector<Rect>& bbpoint, vector<Point>& center, int start_angle, int end_angle){
	Mat z(center.size(),center.size(),CV_32F);
	Mat Ang(center.size(),center.size(),CV_32F);
	int avglength=0;
	for (int j=0; j< center.size();j++){	
		for (int k=0;k<center.size();k++){	
			z.at<float>(j,k) = distance(center[j].x,center[j].y,center[k].x,center[k].y);
			Ang.at<float>(j,k) = angle(center[j].x,center[j].y,center[k].x,center[k].y);
		}
	}

	int VisitStatus[center.size()];
	Point Neigh1, Neigh2,nmax,Neigh;
	// calculate 

	double min ,max;
	for(int j=0; j< center.size() ;j++)
		VisitStatus[j]=-1;

	int index,index1,index2;
	avglength=50 ;
	for(int j=0; j < center.size() ;j++){
		min=INT_MAX;
		Neigh1.x=-1;Neigh1.y=-1;
		for (int k=0;k<center.size();k++){
			if( z.at<float>(j,k) < min && z.at<float>(j,k)>0 && z.at<float>(j,k) < 2*avglength && j!=k && Ang.at<float>(j,k)<end_angle && Ang.at<float>(j,k)>start_angle ){
				min = z.at<float>(j,k);
				Neigh1.x=k;Neigh1.y=k;
			}
		}

		min=INT_MAX;
		Neigh2.x=-1;Neigh2.y=-1;
		for (int k=0;k<center.size();k++){
			if( z.at<float>(j,k) < min && z.at<float>(j,k)>0 && z.at<float>(j,k) < 2*avglength && k!=Neigh1.x && j!=k  && Ang.at<float>(j,k)<end_angle && Ang.at<float>(j,k)>start_angle){
				min = z.at<float>(j,k);
				Neigh2.x=k;Neigh2.y=k;
			}
		}
		if(Neigh1.x!=-1){
			Neigh.x=Neigh1.x;Neigh1.x=Neigh1.y;
		}
		else if(Neigh2.x!=-1){
			Neigh.x=Neigh2.x;Neigh1.x=Neigh2.y;
		}
		else
		 	continue;
	
		if(VisitStatus[j]==-1){
			// find the index of BB to link , current BB or prev text box formed :
			if(VisitStatus[Neigh.x]==-1)
				index2=Neigh.x;
			else 
				index2=VisitStatus[Neigh.x];
			index1=j;
		}
		else {
	        if(VisitStatus[Neigh.x]==-1)
                index2=Neigh.x;
            else
                index2=VisitStatus[Neigh.x];
			index1=VisitStatus[j];
		}
        
        Rect temp;
        // link two neighbours
        temp=LinkBB(bbpoint[index1],bbpoint[index2]);
        // out of the two bounding box check the first box and fill the values : 
        if(bbpoint[index1].x<=bbpoint[index2].x){
           	bbpoint[index1]=temp;
            VisitStatus[index2]=index1;
            VisitStatus[index1]=index1;
			indexmatch(VisitStatus,center.size(),index2,index1);
			VisitStatus[j]=index1;
        }
        else{
            bbpoint[index2]=temp;
            VisitStatus[index2]=index2;
            VisitStatus[index1]=index2;
			indexmatch(VisitStatus,center.size(),index1,index2);
			VisitStatus[j]=index2;
        }
	}
	int n=center.size();
	vector<int> vec;
	for(int j=0;j<n;j++){
		if (VisitStatus[j]!=-1)
			vec.push_back(VisitStatus[j]);
        else
	        vec.push_back(j);
	}

	sort( vec.begin(), vec.end() );
	vec.erase( unique( vec.begin(), vec.end() ), vec.end() );
	return vec;
}

//--------------------------------function------------------------------------//
string type2str(int type){
	string r;
	uchar depth = type & CV_MAT_DEPTH_MASK;
	uchar chans = 1 + (type >> CV_CN_SHIFT);
	switch ( depth ) {
	case CV_8U:  r = "8U"; break;
	case CV_8S:  r = "8S"; break;
	case CV_16U: r = "16U"; break;
	case CV_16S: r = "16S"; break;
	case CV_32S: r = "32S"; break;
	case CV_32F: r = "32F"; break;
	case CV_64F: r = "64F"; break;
	default:     r = "User"; break;
	}
	r += "C";
	r += (chans+'0');
	return r;
}

//--------------------------------function------------------------------------//
int main(int argc , char** argv)
{
	if( argc < 3){
	   printf( " No image data \n " );
	   return -1;
	}
	char * imageName=argv[1];
	char * lang=argv[2];
	int start_angle = -25;
	int end_angle = 25;
	string language(lang);
	FILE *fptr;
	Mat image,bbimage,bbimage1,bbimage2;
	image=imread(imageName,1);
	// make a copy of the image 
	bbimage=image.clone();
	bbimage1=image.clone();
	bbimage2=image.clone();
	if(argc != 3 ){
		start_angle = atoi(argv[3]);
		end_angle = atoi(argv[4]);
	}
	// grayscale image 
	Mat gray_image;
 	cvtColor( image, gray_image, COLOR_RGB2GRAY );
 	// Stretching the pixels to remove watermark temporarily
 	imadjust(gray_image,gray_image,10);    //contrast streching

	Mat dst;
	adaptiveThreshold(gray_image,dst, 255,CV_ADAPTIVE_THRESH_GAUSSIAN_C, CV_THRESH_BINARY_INV, 35, 2);    // binary conversion
	//cv::imshow("adaptive", dst);

	Mat bwimage = dst.clone();
	vector<Rect> bbpoint;
	Mat labelImage(image.size(),CV_32S);
	Mat stats;
	Mat centroid;

	//Calculate the connected Components . This one uses flood fill method
	int nLabels = connectedComponentsWithStats(dst, labelImage,stats,centroid,8,CV_32S);
	Size s = image.size();
	vector<Rect> temp;
	vector<string> tessout;
	
	if(language=="hin"){
		for(int i=0;i<nLabels;i++){
			Rect rect ;
			rect.x=stats.at<int>(i,0);
			rect.y=stats.at<int>(i,1);
			rect.width=stats.at<int>(i,2);
			rect.height=stats.at<int>(i,3);
			Mat dst1=bwimage(rect);

			if (rect.width < (0.2*s.width) && rect.width > (0.01*s.width) && rect.height < (0.1*s.height) && rect.height > (0.01*s.height) && (rect.width*rect.height) > 0.0000001* s.height*s.width  ){
				temp.push_back(rect);
				imwrite( "temp1.jpg", dst1 );
				system("tesseract -l hin -psm 6 temp1.jpg tess");
				string line;
				std::ifstream infile("tess.txt");	

				getline(infile,line);
				tessout.push_back(line);
				infile.close();		
			}
		}
	}
	else if(language=="ben"){
		for(int i=0;i<nLabels;i++){
			Rect rect ;
			rect.x=stats.at<int>(i,0);
			rect.y=stats.at<int>(i,1);
			rect.width=stats.at<int>(i,2);
			rect.height=stats.at<int>(i,3);
			Mat dst1=bwimage(rect);

			if (rect.width < (0.2*s.width) && rect.width > (0.01*s.width) && rect.height < (0.1*s.height) && rect.height > (0.01*s.height) && (rect.width*rect.height) > 0.0000001* s.height*s.width  ){
				temp.push_back(rect);
				imwrite( "temp1.jpg", dst1 );
				system("tesseract -l ben -psm 6 temp1.jpg tess");
				string line;
				std::ifstream infile("tess.txt");	

				getline(infile,line);
				tessout.push_back(line);
				infile.close();		
			}
		}
	}
	else if(language=="eng"){
		vector<Point> center;
		FileStorage ffs("ann.yml", FileStorage::READ);
		Ptr<cv::ml::ANN_MLP> ann = Algorithm::read<cv::ml::ANN_MLP>(ffs.root());
		vector<int> removelabel;
		for(int i=0;i<nLabels;i++){
			Rect rect ;
			rect.x=stats.at<int>(i,0);
			rect.y=stats.at<int>(i,1);
			rect.width=stats.at<int>(i,2);
			rect.height=stats.at<int>(i,3);
			if (rect.width < (0.1*s.width) && rect.height < (0.1*s.height) && (rect.width*rect.height) > 0.0000001* s.height*s.width){		
				Mat dst3=dst(rect);
				Mat dst2;
				Mat sample;
				resize(dst3,dst2,size,INTER_CUBIC);    //  cubic interpolation -> rectangle to square matrix (50*50)
				dst2.convertTo(dst2,CV_32FC1);

				sample.push_back(dst2.reshape(1,1));   // one row of 2500 features
				Mat response(1,1,CV_32FC1);

				ann->predict(sample,response);    //// text or non-text component (1 and -1)
				if(response.at<float>(0,0) >0){
					bbpoint.push_back(rect);
					Point temp;
					temp.x=rect.x+rect.width/2;
					temp.y=rect.y+rect.height/2;
					center.push_back(temp);
				}
				else{
					removelabel.push_back(i);
				}
			}
		}

		vector<int> vec;
		vec= LineGenerate(bbpoint,center,start_angle,end_angle);    // concatinating adjacent text box (25 degree)

		for (int j=0;j<vec.size();j++){
			Mat ocrimage=bwimage(bbpoint[vec[j]]);
			temp.push_back(bbpoint[vec[j]]);
			imwrite( "temp1.jpg", ocrimage );
			system("tesseract -psm 6 temp1.jpg tess");
			string line;
			std::ifstream infile("tess.txt");	

			getline(infile,line);
			tessout.push_back(line);
			infile.close();		
		}
	}
	pushBB2file(temp,tessout,imageName);
	string filename = "tess.txt";
	remove(filename.c_str());
	filename = "temp1.jpg";
	remove(filename.c_str());
 	return 0;
}